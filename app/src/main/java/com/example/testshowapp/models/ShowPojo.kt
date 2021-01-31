package com.example.testshowapp.models


import com.google.gson.annotations.SerializedName

data class ShowPojo(
    @SerializedName("show")
    val show: Show
)