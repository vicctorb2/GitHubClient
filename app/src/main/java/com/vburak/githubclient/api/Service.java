package com.vburak.githubclient.api;

import com.vburak.githubclient.model.GitHubUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/search/users?q=type:user")
    Call<GitHubUserResponse> getUsers();
}
