package com.vburak.githubclient.model;

import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
