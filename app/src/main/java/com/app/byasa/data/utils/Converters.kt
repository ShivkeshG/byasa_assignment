package com.app.byasa.data.utils

import androidx.room.TypeConverter
import com.app.byasa.data.models.ProductColor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Converters {
    @TypeConverter
    fun listToJson(list: List<ProductColor?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun jsonToList(value: String?): List<ProductColor>  {
        val gson = Gson()
        return gson.fromJson(value, Array<ProductColor>::class.java).toList()
    }
}