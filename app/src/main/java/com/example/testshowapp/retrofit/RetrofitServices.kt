package com.example.testshowapp.retrofit

import retrofit2.http.GET
import com.example.testshowapp.model.ModelsForGson
import retrofit2.Call
import retrofit2.http.Query

interface RetrofitServices {
    @GET("shows")
    fun getData(@Query("q") query: String): Call<List<ModelsForGson>>
}