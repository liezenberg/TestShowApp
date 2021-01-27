package com.example.testshowapp.model


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    val average: Int
)