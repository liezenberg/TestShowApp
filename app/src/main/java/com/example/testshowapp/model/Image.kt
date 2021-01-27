package com.example.testshowapp.model


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("medium")
    val medium: String
)