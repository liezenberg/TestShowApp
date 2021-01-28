package com.example.testshowapp.model


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: Self?
)