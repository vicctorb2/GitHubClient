package com.vburak.githubclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vburak.githubclient.R;
import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.GitHubUserResponse;
import com.vburak.githubclient.model.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vburak.githubclient.view.MainActivity.authHeader;
import static com.vburak.githubclient.view.MainActivity.getMainUser;


/**  Fragment for main activity, which contain logged user profile information**/
public class YourProfileFragment extends Fragment {

    View view;
    TextView usernameTV;
    TextView nameTV;
    TextView companyTV;
    TextView emailTV;
    TextView privateReposCountTV;
    TextView gistsCountTV;
    TextView ownedReposTV;
    ImageView imageView;
    GitHubUser mainUser;
    Button goToReposButton;


    /** List of repositories of your profile**/
    List<Repository> repositories;

    public YourProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.yourprofile_fragment,container,false);

        //getting main user from MainActivity
        mainUser = getMainUser();
        initUI();
        return view;
    }


    /** GUI initialization,setting all required fields and onClickLsteners**/
    private void initUI() {
        usernameTV = (TextView) view.findViewById(R.id.your_profile_username);
        nameTV = (TextView) view.findViewById(R.id.your_profile_name);
        imageView = (ImageView) view.findViewById(R.id.your_profile_image);
        companyTV = (TextView) view.findViewById(R.id.your_profile_company);
        emailTV = (TextView) view.findViewById(R.id.your_profile_email);
        privateReposCountTV = (TextView) view.findViewById(R.id.private_count_id);
        gistsCountTV = (TextView) view.findViewById(R.id.gists_count_id);
        ownedReposTV = (TextView) view.findViewById(R.id.owned_count_id);
        goToReposButton = (Button) view.findViewById(R.id.go_to_repos_button);
        Picasso.with(getContext())
                .load(mainUser.getImage())
                .placeholder(R.drawable.logo)
                .into(imageView);
        usernameTV.setText(mainUser.getUsername());
        nameTV.setText(mainUser.getName());
        if (mainUser.getEmail()!=null){
            emailTV.setText(mainUser.getEmail());
        }
        if (mainUser.getCompany()!=null){
            companyTV.setText(mainUser.getCompany());
        }
        privateReposCountTV.setText(String.valueOf(mainUser.getPrivateReposCount()));
        gistsCountTV.setText(String.valueOf(mainUser.getGistsCount()));
        ownedReposTV.setText(String.valueOf(mainUser.getOwnedPrivateReposCount()));

        goToReposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service apiService = Client.getClient().create(Service.class);
                Call<List<Repository>> call = apiService.getMyRepos(authHeader);
                call.enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {

                        // getting repositories of your profile list and starting the intent which shows an activity with repos
                        ArrayList<Repository> repositories = new ArrayList<>(response.body());
                        Intent intent = new Intent(getActivity(),RepositoriesActivity.class);
                        intent.putExtra("data",repositories);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {

                    }
                });
            }
        });
    }

    public List<Repository> getRepositories() {
        return repositories;
    }
}
