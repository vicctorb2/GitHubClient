package com.vburak.githubclient.api;

import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.GitHubUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Service {


    @GET("/search/users?q=type:user")
    Call<GitHubUserResponse> getUsers(@Header("Authorization") String authHeader,@Query("page") int page, @Query("per_page") int per_page);

    @GET("/search/users")
    Call<GitHubUserResponse> getUsersFromSearch(@Header("Authorization") String authHeader, @Query("q") String name);

    @GET("/user")
    Call<GitHubUser> mainUser(@Header("Authorization") String authHeader);

}
