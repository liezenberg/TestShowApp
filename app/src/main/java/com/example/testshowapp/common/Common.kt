package com.example.testshowapp.common

import com.example.testshowapp.retrofit.RetrofitServices


object Common {
    const val BASE_URL = "https://api.tvmaze.com"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}