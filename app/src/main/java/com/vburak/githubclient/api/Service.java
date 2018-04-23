package com.vburak.githubclient.api;

import com.vburak.githubclient.model.GitHubUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("/search/users?q=type:user")
    Call<GitHubUserResponse> getUsers(@Query("page") int page, @Query("per_page") int per_page);

    @GET("/search/users")
    Call<GitHubUserResponse> getUsersFromSearch(@Query("q") String name);
}
