package com.example.posts.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostService {

    private static PostApi postApi;

    public static PostApi getInstance() {
        if (postApi == null) {
            postApi = buildRetrofit();
        }
        return postApi;
    }

    private static PostApi buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://android-3-mocker.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PostApi.class);
    }

}
