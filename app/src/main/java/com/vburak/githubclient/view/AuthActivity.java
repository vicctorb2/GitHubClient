package com.vburak.githubclient.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vburak.githubclient.R;
import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** An activity for Basic Authentication to GitHub and app **/
public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameET;
    EditText passwordET;
    Button sigInButton;
    ProgressBar progressBar;
    RelativeLayout rootLayout;
    private static Service apiService;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        rootLayout = findViewById(R.id.root_layout_auth);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        sigInButton = findViewById(R.id.button_sign_in);
        progressBar = findViewById(R.id.progressBarAuth);
        sigInButton.setOnClickListener(this);
        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() != R.id.username && v.getId() != R.id.password) {
                    hideKeyboard();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                tryToSignIn();
                break;
        }
    }


    //for hiding keyboard when touch not on edittext views
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


    //trying to sign in using setted username and password
    private void tryToSignIn() {
        //if edittexts not empty
        if (!usernameET.getText().toString().equals("") && !passwordET.getText().toString().equals("")) {
            progressBar.setVisibility(View.VISIBLE);

            //get
            final String username = usernameET.getText().toString();
            final String password = passwordET.getText().toString();

            //creating an authHeader with Base64 crypt
            String base = username + ":" + password;
            final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            apiService = Client.getClient().create(Service.class);
            Call call = apiService.mainUser(authHeader);
            call.enqueue(new Callback<GitHubUser>() {
                @Override
                public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                    //if status is OK go to main activity
                    if (response.code() == 200) {
                        GitHubUser mainUser = response.body();
                        Intent intent = new Intent();
                        intent.putExtra("authHeader", authHeader);
                        intent.putExtra("username", username);
                        intent.putExtra("mainUser", mainUser);
                        setResult(RESULT_OK, intent);
                        progressBar.setVisibility(View.INVISIBLE);
                        finish();
                        Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Username or password is incorrect!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<GitHubUser> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong, check you internet connection!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Some fields are empty! Try again.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
