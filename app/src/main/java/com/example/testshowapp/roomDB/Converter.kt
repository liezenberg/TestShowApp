package com.example.testshowapp.roomDB

import androidx.room.TypeConverter
import com.example.testshowapp.models.Image
import com.example.testshowapp.models.Rating
import com.example.testshowapp.models.Show
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken




class Converters {
    @TypeConverter
    fun fromStringToImage(value: String?): Image? {
       return value?.let { Image(value) }
    }
    @TypeConverter
    fun imageToString(img: Image?): String? {
        return img?.original
    }

    @TypeConverter
    fun fromDoubleToImage(value: Double?): Rating? {
        return value?.let { Rating(value) }
    }
    @TypeConverter
    fun RatingToDouble(rating: Rating?): Double? {
        return rating?.average
    }

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        return Gson().fromJson(value, Array<String>::class.java).toList()
    }
    @TypeConverter
    fun fromList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromBoolean(bool:Boolean):Int{
        if (bool){
            return 1
        }
        return 0
    }
    @TypeConverter
    fun fromIntToBoolean(value:Int):Boolean{
        if (value == 1){
            return true
        }
        return false
    }

}