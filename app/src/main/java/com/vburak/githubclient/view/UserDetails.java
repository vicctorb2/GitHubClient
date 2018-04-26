package com.vburak.githubclient.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vburak.githubclient.R;
import com.vburak.githubclient.model.GitHubUser;

public class UserDetails extends Activity {

    View view;
    TextView usernameTV;
    TextView nameTV;
    TextView companyTV;
    TextView emailTV;

    ImageView imageView;
    GitHubUser detailedUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_layout);
        initUI();
    }

    private void initUI() {
        detailedUser = getIntent().getParcelableExtra("detailedUser");
        usernameTV = (TextView) findViewById(R.id.detailed_username);
        nameTV = (TextView) findViewById(R.id.detailed_name);
        imageView = (ImageView) findViewById(R.id.detailed_profile_image);
        companyTV = (TextView) findViewById(R.id.detailed_company);
        emailTV = (TextView) findViewById(R.id.detailed_email);

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
