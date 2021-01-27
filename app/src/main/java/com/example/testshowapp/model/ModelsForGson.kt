package com.example.testshowapp.model


import com.google.gson.annotations.SerializedName

data class ModelsForGson(
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("image")
    val image: Image,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("url")
    val url: String,
    val isFavorite : Boolean
)