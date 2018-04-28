package com.vburak.githubclient.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vburak.githubclient.R;
import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.GitHubUserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vburak.githubclient.view.MainActivity.authHeader;

public class UsersListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    protected static RecyclerView recyclerView;
    private static List<GitHubUser> gitHubUsersList;
    protected static List<GitHubUser> filteredUsersList;
    static Service apiService;
    private static int currentJsonResponsePage = 1;
    private static int usersPerPage = 50;
    static RecyclerViewAdapter recyclerViewAdapter;
    static RecyclerViewAdapter filteredViewAdapter;

    //flag for demonstrate is our call still pending to github api
    static boolean loading;
    static ProgressBar progressBar;

    //pul to refresh component
    private static SwipeRefreshLayout swipeRefreshLayout;


    public UsersListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        view = inflater.inflate(R.layout.userslist_fragment, container, false);
        initUI();
        //loading users list
        loadAllUsersList();
        return view;
    }


    //loads the users list from github api with pagination
    //current page of response and how man users on it setted through @params (currentJsonResponsePage, usersPerPage)
    public static void loadAllUsersList() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        apiService = Client.getClient().create(Service.class);

        Call<GitHubUserResponse> call = apiService.getUsers(authHeader, currentJsonResponsePage, usersPerPage);
        call.enqueue(new Callback<GitHubUserResponse>() {
            @Override
            public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                try {
                    if (response.isSuccessful() && response.code() != 403) {
                        List<GitHubUser> responseItemsList = new ArrayList<>(response.body().getItems());
                        gitHubUsersList.addAll(responseItemsList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.scrollToPosition(gitHubUsersList.size() - responseItemsList.size() - 1);
                        //incrementing current json response page
                        currentJsonResponsePage++;
                        loading=false;
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    recyclerView.setAdapter(recyclerViewAdapter);
                    loading=false;
                }

            }

            @Override
            public void onFailure(Call<GitHubUserResponse> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }
    //initialization of UI components and setting recycler view listener for pagination
    private void initUI() {
        progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        gitHubUsersList = new ArrayList<>();
        filteredUsersList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), gitHubUsersList);
        filteredViewAdapter = new RecyclerViewAdapter(getContext(), filteredUsersList);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_id);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        //for pagination when scrolled list of users to the end
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastvisibleitemposition = mLayoutManager.findLastVisibleItemPosition();

                if (lastvisibleitemposition == recyclerViewAdapter.getItemCount() - 1) {

                    if (!loading) {
                        loading = true;
                        loadAllUsersList();
                    }
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        gitHubUsersList.clear();
        currentJsonResponsePage=1;
        loadAllUsersList();
        swipeRefreshLayout.setRefreshing(false);
    }
}
