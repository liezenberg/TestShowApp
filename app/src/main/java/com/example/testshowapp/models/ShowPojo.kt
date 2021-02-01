package com.example.testshowapp.models


import com.google.gson.annotations.SerializedName

//POJO for parsing Json to object
data class ShowPojo(
    @SerializedName("show")
    val show: Show
)