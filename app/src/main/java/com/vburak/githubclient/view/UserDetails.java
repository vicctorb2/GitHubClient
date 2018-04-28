package com.vburak.githubclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vburak.githubclient.R;
import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vburak.githubclient.view.MainActivity.authHeader;
/** User detailed info activity**/
public class UserDetails extends Activity {

    View view;
    TextView usernameTV;
    TextView nameTV;
    TextView companyTV;
    TextView emailTV;

    ImageView imageView;
    GitHubUser detailedUser;

    Button goToReposButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_layout);
        initUI();
    }


    //UI initializaton
    private void initUI() {
        detailedUser = getIntent().getParcelableExtra("detailedUser");
        usernameTV = (TextView) findViewById(R.id.detailed_username);
        nameTV = (TextView) findViewById(R.id.detailed_name);
        imageView = (ImageView) findViewById(R.id.detailed_profile_image);
        companyTV = (TextView) findViewById(R.id.detailed_company);
        emailTV = (TextView) findViewById(R.id.detailed_email);
        goToReposButton = (Button) findViewById(R.id.detailed_go_to_repos_button);

        Picasso.with(this)
                .load(detailedUser.getImage())
                .placeholder(R.drawable.logo)
                .into(imageView);
        usernameTV.setText(detailedUser.getUsername());
        if (detailedUser.getName()!=null){
            nameTV.setText(detailedUser.getName());
        }
        else{
            nameTV.setText(detailedUser.getUsername());
        }
        if (detailedUser.getEmail()!=null){
            emailTV.setText(detailedUser.getEmail());
        }
        if (detailedUser.getCompany()!=null){
            companyTV.setText(detailedUser.getCompany());
        }
        //binding listener to get repos list from api and go to the repositories activity
        goToReposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service apiService = Client.getClient().create(Service.class);
                Call<List<Repository>> call = apiService.getUserRepos(authHeader,detailedUser.getUsername());
                call.enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                        ArrayList<Repository> repositories = new ArrayList<>(response.body());
                        Intent intent = new Intent(getApplicationContext(),RepositoriesActivity.class);
                        //set data for intent containing all repositories of the user
                        intent.putExtra("data",repositories);
                        //starting repos activity
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
