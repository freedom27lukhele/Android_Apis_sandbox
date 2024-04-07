package com.example.androidapis.api;

import com.example.androidapis.models.RecyclerData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApi {

    @POST("users")
    Call<RecyclerData>createPost(@Body RecyclerData dataModal);

    @GET("WO6S")
    Call<ArrayList<RecyclerData>> getAllCourses();
}
