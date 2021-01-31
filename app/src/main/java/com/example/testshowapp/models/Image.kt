package com.example.testshowapp.models


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("original")
    val original: String?
)