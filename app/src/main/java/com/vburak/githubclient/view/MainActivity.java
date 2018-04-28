package com.vburak.githubclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vburak.githubclient.R;
import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.GitHubUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vburak.githubclient.view.UsersListFragment.apiService;
import static com.vburak.githubclient.view.UsersListFragment.filteredUsersList;
import static com.vburak.githubclient.view.UsersListFragment.filteredViewAdapter;
import static com.vburak.githubclient.view.UsersListFragment.recyclerView;
import static com.vburak.githubclient.view.UsersListFragment.recyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    public SearchView searchView;
    static String authHeader;
    static String username;
    static GitHubUser mainUser;

    public static GitHubUser getMainUser() {
        return mainUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //starting authorization activity and eaiting for result
        Intent intent = new Intent(this, AuthActivity.class);
        startActivityForResult(intent, 1);
    }


    //when result presents initialize UI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //user auth params from AUTH intent
        authHeader = data.getStringExtra("authHeader");
        username = data.getStringExtra("username");
        mainUser = data.getParcelableExtra("mainUser");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //tabs setting
        adapter.addFragment(new YourProfileFragment(), "Your profile");
        adapter.addFragment(new UsersListFragment(), "Users list");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
    }


    //menu (search items) creating
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        if (newText.equals("") || newText.equals(" ")) {
            recyclerView.setAdapter(recyclerViewAdapter);
            return false;
        } else {
            apiService = Client.getClient().create(Service.class);
            Call<GitHubUserResponse> call = apiService.getUsersFromSearch(authHeader, newText);
            call.enqueue(new Callback<GitHubUserResponse>() {
                @Override
                public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                    try {
                        if (response.code() != 403) {
                            filteredUsersList.clear();
                            filteredUsersList.addAll(response.body().getItems());
                            recyclerView.swapAdapter(filteredViewAdapter, true);
                        } else {
                            Toast.makeText(getApplicationContext(), "Github API search rate limit (30 per minute). Please try again later.", Toast.LENGTH_LONG).show();
                        }
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                        recyclerView.swapAdapter(recyclerViewAdapter, true);
                    }
                }

                @Override
                public void onFailure(Call<GitHubUserResponse> call, Throwable t) {
                    recyclerView.swapAdapter(recyclerViewAdapter, true);
                }
            });
        }
        return false;
    }
}
