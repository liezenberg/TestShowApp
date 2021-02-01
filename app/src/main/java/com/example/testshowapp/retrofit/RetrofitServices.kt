package com.example.testshowapp.retrofit

import retrofit2.http.GET
import com.example.testshowapp.models.ShowPojo
import retrofit2.Call
import retrofit2.http.Query

interface RetrofitServices {
    @GET("search/shows")
    fun getData(@Query("q") query: String): Call<List<ShowPojo>>
}