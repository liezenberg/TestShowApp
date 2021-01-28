package com.example.testshowapp.model


import com.google.gson.annotations.SerializedName

data class ShowPojo(
    @SerializedName("show")
    val show: Show
)