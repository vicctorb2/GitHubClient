package com.vburak.githubclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.GitHubUserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends Fragment {

    View view;
    private static RecyclerView recyclerView;
    private static List<GitHubUser> gitHubUsersList;
    private static List<GitHubUser> filteredUsersList;
    private static List<GitHubUser> lastSavedList;
    private GitHubUser user;
    private Button loadMoreButton;
    static Service apiService;
    private static int currentJsonResponsePage = 1;
    private static int usersPerPage = 50;
    static RecyclerViewAdapter recyclerViewAdapter;
    static RecyclerViewAdapter filteredViewAdapter;


    public UsersListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        view = inflater.inflate(R.layout.userslist_fragment, container, false);
        initUI();
        loadJSONfromAPI();
        return view;
    }

    public static void loadJSONfromAPI() {
        try {
            apiService = Client.getClient().create(Service.class);
            Call<GitHubUserResponse> call = apiService.getUsers(currentJsonResponsePage, usersPerPage);
            call.enqueue(new Callback<GitHubUserResponse>() {
                @Override
                public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                    try {
                        List<GitHubUser> responseItemsList = new ArrayList<>();
                        responseItemsList.addAll(response.body().getItems());
                        gitHubUsersList.addAll(responseItemsList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.scrollToPosition(gitHubUsersList.size() - responseItemsList.size() - 1);
                        currentJsonResponsePage++;
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<GitHubUserResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

    }

    public static void searchUser(final String username, boolean flag) {
        if (flag) {
            try {
                apiService = Client.getClient().create(Service.class);
                Call<GitHubUserResponse> call = apiService.getUsersFromSearch(username);
                call.enqueue(new Callback<GitHubUserResponse>() {
                    @Override
                    public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                        try {
                            List<GitHubUser> filteredList = new ArrayList<>();
                            filteredList = response.body().getItems();
                            filteredUsersList.clear();
                            filteredUsersList.addAll(filteredList);
                            recyclerView.setAdapter(filteredViewAdapter);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GitHubUserResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.e("ERROR", ex.getMessage());
            }
        } else {
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    private void initUI() {
        gitHubUsersList = new ArrayList<>();
        filteredUsersList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), gitHubUsersList);
        filteredViewAdapter = new RecyclerViewAdapter(getContext(), filteredUsersList);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
