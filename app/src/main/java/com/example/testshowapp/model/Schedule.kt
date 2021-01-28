package com.example.testshowapp.model


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("days")
    val days: List<Any>?,
    @SerializedName("time")
    val time: String?
)