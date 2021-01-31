package com.example.testshowapp.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "FavoritesEntity")
data class Show(
    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    val genres: List<String>?,
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val ID: Int?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: Image?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,
    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    val rating: Rating?,
    @Expose
    @ColumnInfo(name = "isFavorite")
    val isFavorite:Boolean
): Serializable