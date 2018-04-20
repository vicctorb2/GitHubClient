package com.vburak.githubclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
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
    private RecyclerView recyclerView;
    private List<GitHubUser> gitHubUsersList;
    private GitHubUser user;
    RecyclerViewAdapter recyclerViewAdapter;
    public UsersListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userslist_fragment,container,false);
        initUI();
        loadJSONfromAPI();
        return view;
    }

    private void loadJSONfromAPI() {
        try{
            Service apiService = Client.getClient().create(Service.class);
            Call<GitHubUserResponse> call = apiService.getUsers();
            call.enqueue(new Callback<GitHubUserResponse>() {
                @Override
                public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                    gitHubUsersList = response.body().getItems();
                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(),gitHubUsersList);
                    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_id);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(recyclerViewAdapter);
                }

                @Override
                public void onFailure(Call<GitHubUserResponse> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception ex){
            Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void initUI() {
        gitHubUsersList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
