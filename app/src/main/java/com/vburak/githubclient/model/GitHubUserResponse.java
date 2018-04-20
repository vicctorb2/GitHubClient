package com.vburak.githubclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitHubUserResponse {
    @SerializedName("items")
    @Expose
    private List<GitHubUser> items;

    public List<GitHubUser> getItems() {
        return items;
    }

    public void setItems(List<GitHubUser> items) {
        this.items = items;
    }
}
