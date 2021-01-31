package com.example.testshowapp.models


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    val average: Double?
)