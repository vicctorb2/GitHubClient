package com.vburak.githubclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vburak.githubclient.model.GitHubUser;

import java.util.ArrayList;
import java.util.List;

public class UsersListFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private List<GitHubUser> gitHubUsersList;
    public UsersListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userslist_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_id);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),gitHubUsersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gitHubUsersList = new ArrayList<>();
        gitHubUsersList.add(new GitHubUser("Victor Buryak",R.drawable.vicctorb,"https://github.com/vicctorb2"));
        gitHubUsersList.add(new GitHubUser("Artsiom Stulba",R.drawable.vicctorb,"https://github.com/stulbaart"));
    }
}
