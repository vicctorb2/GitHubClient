package com.vburak.githubclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameET;
    EditText passwordET;
    Button sigInButton;
    private static Service apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
        sigInButton = (Button) findViewById(R.id.button_sign_in);
        sigInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                tryToSignIn();
                break;
        }
    }

    private void tryToSignIn() {
        if (!usernameET.getText().toString().equals("") && !passwordET.getText().toString().equals("")) {
            final String username = usernameET.getText().toString();
            final String password = passwordET.getText().toString();
            String base = username + ":" + password;
            final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            apiService = Client.getClient().create(Service.class);
            Call call = apiService.mainUser(authHeader);
            call.enqueue(new Callback<GitHubUser>() {
                @Override
                public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                    if (response.code() == 200) {
                        GitHubUser mainUser = response.body();
                        Intent intent = new Intent();
                        intent.putExtra("authHeader", authHeader);
                        intent.putExtra("username", username);
                        intent.putExtra("mainUser",mainUser);
                        setResult(RESULT_OK, intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"Welcome!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Username or password is incorrect!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GitHubUser> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Something went wrong, check you internet connection!",Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Some fields are empty! Try again.",Toast.LENGTH_LONG).show();
        }
    }
}
