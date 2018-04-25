package com.vburak.githubclient;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUserResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vburak.githubclient.UsersListFragment.apiService;
import static com.vburak.githubclient.UsersListFragment.authHeader;
import static com.vburak.githubclient.UsersListFragment.filteredUsersList;
import static com.vburak.githubclient.UsersListFragment.filteredViewAdapter;
import static com.vburak.githubclient.UsersListFragment.recyclerView;
import static com.vburak.githubclient.UsersListFragment.recyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    public SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UsersListFragment(), "Users list");
        adapter.addFragment(new YourProfileFragment(), "Your profile");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

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
            Call<GitHubUserResponse> call = apiService.getUsersFromSearch(authHeader,newText);
            call.enqueue(new Callback<GitHubUserResponse>() {
                @Override
                public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                    try {
                        if (response.code() != 403) {
                            filteredUsersList.clear();
                            filteredUsersList.addAll(response.body().getItems());
                            recyclerView.swapAdapter(filteredViewAdapter, true);
                        }
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Search API rate limit!",Toast.LENGTH_SHORT).show();
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
