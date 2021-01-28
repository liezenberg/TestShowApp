package com.example.testshowapp.model


import com.google.gson.annotations.SerializedName

data class Externals(
    @SerializedName("imdb")
    val imdb: Any?,
    @SerializedName("thetvdb")
    val thetvdb: Int?,
    @SerializedName("tvrage")
    val tvrage: Any?
)