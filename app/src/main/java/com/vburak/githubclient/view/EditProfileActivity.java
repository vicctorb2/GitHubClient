package com.vburak.githubclient.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vburak.githubclient.R;
import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vburak.githubclient.view.MainActivity.authHeader;
import static com.vburak.githubclient.view.MainActivity.getMainUser;
/** An activity for profile editing. E-mail cant be edited.**/
public class EditProfileActivity extends AppCompatActivity {

    EditText newNameET;
    EditText newCompanyET;
    EditText newLocationET;
    Button submitButton;
    GitHubUser mainUser;

    LinearLayout rootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        mainUser = getMainUser();
        initUI();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {
        newNameET = findViewById(R.id.new_name_ET);
        newCompanyET = findViewById(R.id.new_company_ET);
        newLocationET = findViewById(R.id.new_location_ET);
        rootLayout = findViewById(R.id.root_layout_edit_profile);

        //listener for hiding keyboard when not ET touched
        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() != R.id.new_name_ET && v.getId() != R.id.new_company_ET && v.getId() != R.id.new_location_ET ) {
                    hideKeyboard();
                }
                return false;
            }
        });
        submitButton = findViewById(R.id.submit_button_id);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the fields aren't empty
                if (!newNameET.getText().toString().equals("")) {
                    mainUser.setName(newNameET.getText().toString());
                }
                if (!newCompanyET.getText().toString().equals("")) {
                    mainUser.setCompany(newCompanyET.getText().toString());
                }
                if (!newLocationET.getText().toString().equals("")) {
                    mainUser.setLocation(newLocationET.getText().toString());
                }

                //trying to apply changes via api patch request
                Service apiService = Client.getClient().create(Service.class);
                Call<GitHubUser> call = apiService.updateUserProfile(authHeader, mainUser);
                call.enqueue(new Callback<GitHubUser>() {
                    @Override
                    public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {

                        //getting changed user and set it in main activity
                        mainUser = response.body();
                        MainActivity.setMainUser(mainUser);
                        finish();
                        Toast.makeText(getApplicationContext(),"Changes successfully applied. Pull to refresh",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<GitHubUser> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
    }

    //for hiding keyboard
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
