package com.vburak.githubclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class GitHubUser {

    @SerializedName("login")
    @Expose
    private String username;

    @SerializedName("avatar_url")
    @Expose
    private String image;

    @SerializedName("html_url")
    @Expose
    private String userAccountLink;

    public GitHubUser(String username, String imageUrl, String userAccountLink) {
        this.username = username;
        this.image = imageUrl;
        this.userAccountLink = userAccountLink;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserAccountLink() {
        return userAccountLink;
    }

    public void setUserAccountLink(String userAccountLink) {
        this.userAccountLink = userAccountLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitHubUser that = (GitHubUser) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(image, that.image) &&
                Objects.equals(userAccountLink, that.userAccountLink);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, image, userAccountLink);
    }
}
