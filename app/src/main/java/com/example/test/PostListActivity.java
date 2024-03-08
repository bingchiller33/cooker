package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.test.adapter.PostListAdapter;
import com.example.test.adapter.ProductListAdapter;
import com.example.test.bean.PostInfo;
import com.example.test.webservice.MyApiEndpointInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostListActivity extends AppCompatActivity {

    private List<PostInfo> postInfoList = new ArrayList<>();

    PostListAdapter postListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        retrievePosts();
        RecyclerView recyclerView = findViewById(R.id.recycle_view_post_list);
        postListAdapter = new PostListAdapter(postInfoList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postListAdapter);
    }

    private void retrievePosts() {
        String baseUrl = "https://jsonplaceholder.typicode.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);
        apiService.getPostInfo().enqueue(new Callback<List<PostInfo>>() {
            @Override
            public void onResponse(Call<List<PostInfo>> call, Response<List<PostInfo>> response) {
                postInfoList.addAll(response.body());
                postListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PostInfo>> call, Throwable t) {
                Log.d(getClass().getSimpleName(), "Failed to get posts");
            }
        });
    }
}