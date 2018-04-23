package com.vburak.githubclient;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
        adapter.addFragment(new UsersListFragment(),"Users list");
        adapter.addFragment(new YourProfileFragment(),"Your profile");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public synchronized boolean onQueryTextSubmit(String newText) {
        if (newText.equals("")){
            UsersListFragment.searchUser("",false);
        }else{
            newText = newText.toLowerCase();
            UsersListFragment.searchUser(newText,true);
        }

        return false;
    }

    @Override
    public synchronized boolean onQueryTextChange(String newText) {
        if (newText.equals("")){
            UsersListFragment.searchUser("",false);
        }else{
            newText = newText.toLowerCase();
            UsersListFragment.searchUser(newText,true);
        }
        return false;
    }
}
