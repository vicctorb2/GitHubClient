package com.vburak.githubclient.api;

import com.vburak.githubclient.model.GitHubUser;
import com.vburak.githubclient.model.GitHubUserResponse;
import com.vburak.githubclient.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {


    @GET("/search/users?q=type:user")
    Call<GitHubUserResponse> getUsers(@Header("Authorization") String authHeader,@Query("page") int page, @Query("per_page") int per_page);

    @GET("/search/users")
    Call<GitHubUserResponse> getUsersFromSearch(@Header("Authorization") String authHeader, @Query("q") String name);

    @GET("/user")
    Call<GitHubUser> mainUser(@Header("Authorization") String authHeader);

    @GET("/users/{username}")
    Call<GitHubUser> getSingleUser(@Header("Authorization") String authHeader, @Path("username") String username);

    @GET("/user/repos")
    Call<List<Repository>> getMyRepos(@Header("Authorization") String authHeader);

    @GET("/users/{username}/repos")
    Call<List<Repository>> getUserRepos(@Header("Authorization") String authHeader, @Path("username") String username);

}
