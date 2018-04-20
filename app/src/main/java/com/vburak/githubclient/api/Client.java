package com.vburak.githubclient.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_API_URL = "https://api.github.com";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
