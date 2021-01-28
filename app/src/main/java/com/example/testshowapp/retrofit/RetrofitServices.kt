package com.example.testshowapp.retrofit

import retrofit2.http.GET
import com.example.testshowapp.model.Show
import com.example.testshowapp.model.ShowPojo
import retrofit2.Call
import retrofit2.http.Query

interface RetrofitServices {
    @GET("search/shows")
    fun getData(@Query("q") query: String): Call<List<ShowPojo>>

}