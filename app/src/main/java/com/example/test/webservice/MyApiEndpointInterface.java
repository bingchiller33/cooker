package com.example.test.webservice;

import com.example.test.bean.PostInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyApiEndpointInterface {
    @GET("/posts")
    Call<List<PostInfo>> getPostInfo();

    @GET("/posts/{id}")
    Call<PostInfo> getPostInfo(@Path("id") String id);

}

