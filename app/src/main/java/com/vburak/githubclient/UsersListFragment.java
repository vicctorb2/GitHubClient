package com.vburak.githubclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.GitHubUserResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends Fragment {

    View view;
    protected static RecyclerView recyclerView;
    private static List<GitHubUser> gitHubUsersList;
    protected static List<GitHubUser> filteredUsersList;
    private static List<GitHubUser> lastSavedList;
    private GitHubUser user;
    private Button loadMoreButton;
    static Service apiService;
    private static int currentJsonResponsePage = 1;
    private static int usersPerPage = 50;
    static RecyclerViewAdapter recyclerViewAdapter;
    static RecyclerViewAdapter filteredViewAdapter;
    private String clientid = "09132ee39c453c0aaf9d";
    private String clientSecret = "d0d350cd23c9ca3f5a80e50e5fcfdf6cef258e7f";
    private String redirectUrl = "vburak://githubclient";
    private String username = "vicctorb2";
    private String password = "ptanb7vu";
    static String authHeader;

    public UsersListFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        view = inflater.inflate(R.layout.userslist_fragment, container, false);
        String authStr = username + ":" + password;
        authHeader = "Basic " + Base64.encodeToString(authStr.getBytes(),Base64.NO_WRAP);
        initUI();
        loadJSONfromAPI();
        return view;
    }

    public static void loadJSONfromAPI() {
        apiService = Client.getClient().create(Service.class);
        Call<GitHubUserResponse> call = apiService.getUsers(authHeader,currentJsonResponsePage, usersPerPage);
        call.enqueue(new Callback<GitHubUserResponse>() {
            @Override
            public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                try {
                    if (response.isSuccessful() && response.code() != 403) {
                        List<GitHubUser> responseItemsList = new ArrayList<>(response.body().getItems());
                        gitHubUsersList.addAll(responseItemsList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.scrollToPosition(gitHubUsersList.size() - responseItemsList.size() - 1);
                        currentJsonResponsePage++;
                    }
                    else{
                        System.out.println(response.errorBody().string());
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    recyclerView.setAdapter(recyclerViewAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GitHubUserResponse> call, Throwable t) {
            }
        });

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
