package com.vburak.githubclient.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
/** class for parse an json array response from server, containing github users**/
public class GitHubUserResponse {
    @SerializedName("items")
    @Expose
    private List<GitHubUser> items;

    @SerializedName("total_count")
    @Expose
    private int totalCount;


    public int getTotalCount(){
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<GitHubUser> getItems() {
        return items;
    }

    public void setItems(List<GitHubUser> items) {
        this.items = items;
    }
}
