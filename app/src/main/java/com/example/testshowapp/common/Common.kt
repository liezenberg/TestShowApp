package com.example.testshowapp.common

import android.content.Context
import androidx.room.Room
import com.example.testshowapp.retrofit.RetrofitServices
import com.example.testshowapp.roomDB.RoomDB

const val RATING = "Rating: "
const val GENRES = "Genres: "

object Common {
    private const val BASE_URL = "https://api.tvmaze.com"
    //Get retrofit implementation to send requests
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}