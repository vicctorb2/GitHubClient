package com.vburak.githubclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("access_token")
    @Expose
    String accessToken;

    @SerializedName("token_type")
    @Expose
    String tokenType;

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
