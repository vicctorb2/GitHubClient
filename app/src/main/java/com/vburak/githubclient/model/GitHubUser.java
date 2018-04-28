package com.vburak.githubclient.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;


/** GitHub user parcelable model, contains all required fields. Gson annotation for parsing JSON**/

public class GitHubUser implements Parcelable {

    @SerializedName("login")
    @Expose
    private String username;

    @SerializedName("avatar_url")
    @Expose
    private String image;

    @SerializedName("html_url")
    @Expose
    private String userAccountLink;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("private_gists")
    @Expose
    private int gistsCount;

    @SerializedName("total_private_repos")
    @Expose
    private int privateReposCount;

    @SerializedName("owned_private_repos")
    @Expose
    private int ownedPrivateReposCount;

    @SerializedName("repos_url")
    @Expose
    private String reposUrl;

    public GitHubUser(String username, String imageUrl, String userAccountLink) {
        this.username = username;
        this.image = imageUrl;
        this.userAccountLink = userAccountLink;
    }

    public GitHubUser(Parcel parcel) {
        Bundle bundle = parcel.readBundle();

        name = bundle.getString("name");
        username = bundle.getString("username");
        image = bundle.getString("image");
        userAccountLink = bundle.getString("userAccountLink");
        email = bundle.getString("email");
        company = bundle.getString("company");
        reposUrl = bundle.getString("reposUrl");
        gistsCount = bundle.getInt("gistsCount");
        privateReposCount = bundle.getInt("privateReposCount");
        ownedPrivateReposCount = bundle.getInt("ownedPrivateReposCount");
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getGistsCount() {
        return gistsCount;
    }

    public void setGistsCount(int gistsCount) {
        this.gistsCount = gistsCount;
    }

    public int getPrivateReposCount() {
        return privateReposCount;
    }

    public void setPrivateReposCount(int privateReposCount) {
        this.privateReposCount = privateReposCount;
    }

    public int getOwnedPrivateReposCount() {
        return ownedPrivateReposCount;
    }

    public void setOwnedPrivateReposCount(int ownedPrivateReposCount) {
        this.ownedPrivateReposCount = ownedPrivateReposCount;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public void setName(String name) {
        this.name = name;
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
        return gistsCount == that.gistsCount &&
                privateReposCount == that.privateReposCount &&
                ownedPrivateReposCount == that.ownedPrivateReposCount &&
                Objects.equals(username, that.username) &&
                Objects.equals(image, that.image) &&
                Objects.equals(userAccountLink, that.userAccountLink) &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(company, that.company) &&
                Objects.equals(reposUrl, that.reposUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, image, userAccountLink, name, email, company, gistsCount, privateReposCount, ownedPrivateReposCount, reposUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("company",company);
        bundle.putString("email",email);
        bundle.putString("reposUrl",reposUrl);
        bundle.putInt("gistsCount",gistsCount);
        bundle.putInt("privateReposCount",privateReposCount);
        bundle.putInt("ownedPrivateReposCount",ownedPrivateReposCount);
        bundle.putString("name", name);
        bundle.putString("username", username);
        bundle.putString("image", image);
        bundle.putString("userAccountLink", userAccountLink);
        // сохраняем
        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public GitHubUser createFromParcel(Parcel in) {
            return new GitHubUser(in);
        }

        public GitHubUser[] newArray(int size) {
            return new GitHubUser[size];
        }
    };
}
