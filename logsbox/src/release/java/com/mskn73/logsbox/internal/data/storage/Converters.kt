package com.mskn73.logsbox.internal.data.storage

import androidx.room.TypeConverter
import java.lang.StringBuilder

class Converters {

    @TypeConverter
    fun listToJson(values: List<String>?): String {

        var valueBuilder = StringBuilder()
        values?.forEach { valueBuilder.append(it) }
        return valueBuilder.toString()
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        val list = mutableListOf<String>()

        val array = value.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s)
            }
        }
        return list
    }
}