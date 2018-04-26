package com.vburak.githubclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vburak.githubclient.model.GitHubUser;

import static com.vburak.githubclient.MainActivity.getMainUser;

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

    public YourProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.yourprofile_fragment,container,false);
        mainUser = getMainUser();
        initUI();
        return view;
    }

    private void initUI() {
        usernameTV = (TextView) view.findViewById(R.id.your_profile_username);
        nameTV = (TextView) view.findViewById(R.id.your_profile_name);
        imageView = (ImageView) view.findViewById(R.id.your_profile_image);
        companyTV = (TextView) view.findViewById(R.id.your_profile_company);
        emailTV = (TextView) view.findViewById(R.id.your_profile_email);
        privateReposCountTV = (TextView) view.findViewById(R.id.private_count_id);
        gistsCountTV = (TextView) view.findViewById(R.id.gists_count_id);
        ownedReposTV = (TextView) view.findViewById(R.id.owned_count_id);

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

    }
}
