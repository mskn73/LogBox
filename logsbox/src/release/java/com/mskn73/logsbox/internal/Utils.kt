package com.mskn73.logsbox.internal

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object Utils {
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    fun formatAsPrettyJson(text: String) = try {
        gson.toJson(JsonParser.parseString(text).getAsJsonObject())
    } catch (e: Exception) {
        text
    }
}