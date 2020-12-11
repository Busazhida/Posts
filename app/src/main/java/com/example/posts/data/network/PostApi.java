package com.example.posts.data.network;

import com.example.posts.data.model.PostModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostApi {

    @GET("posts")
    Call<ArrayList<PostModel>> getPosts();

    @POST("posts")
    Call<PostModel> sendPost(@Body PostModel model);

    @PUT("posts/{postId}")
    Call<PostModel> updatePost(@Path ("postId") String id,  @Body PostModel model);
}
