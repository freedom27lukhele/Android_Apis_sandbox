package com.example.androidapis.api;

import com.example.androidapis.models.DataModal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {

    @POST("users")
    Call<DataModal>createPost(@Body DataModal dataModal);
}
