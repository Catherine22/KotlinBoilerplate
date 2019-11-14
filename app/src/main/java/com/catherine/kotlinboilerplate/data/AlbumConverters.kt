package com.catherine.kotlinboilerplate.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class AlbumConverters {

    @TypeConverter
    fun listToJson(value: List<Album>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Album> {
        val objects = Gson().fromJson(value, Array<Album>::class.java) as Array<Album>
        return objects.toList()
    }
}