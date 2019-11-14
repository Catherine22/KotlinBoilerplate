package com.catherine.kotlinboilerplate.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class PostConverters {

    @TypeConverter
    fun listToJson(value: List<Post>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Post> {
        val objects = Gson().fromJson(value, Array<Post>::class.java) as Array<Post>
        return objects.toList()
    }
}