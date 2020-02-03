package com.mskn73.logsbox.internal.data.storage

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.mskn73.logsbox.internal.LogsDispatcher
import com.mskn73.logsbox.internal.domain.Log
import kotlinx.coroutines.withContext

internal class LogsDataSource(private val database: LogsDatabase) {
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    suspend fun getTypes(): List<String> = withContext(LogsDispatcher.io) {
        database.logsDao().getTypes()
    }

    suspend fun getAllByType(type: String): List<Log> = withContext(LogsDispatcher.io) {
        database.logsDao().getAllByType(type).toDomain()
    }

    suspend fun save(log: Log) = withContext(LogsDispatcher.io) {
        database.logsDao().insert(
            with(log) {
                LogEntity(
                    title = title,
                    type = type,
                    requestHeaders = requestHeaders,
                    requestBody = formatAsPrettyJson(requestBody),
                    responseHeaders = responseHeaders,
                    responseBody = formatAsPrettyJson(responseBody),
                    timeMillis = timeMillis,
                    responseTime = responseTime
                )
            }
        )
    }

    private fun formatAsPrettyJson(text: String) = try {
        gson.toJson(JsonParser.parseString(text).getAsJsonObject())
    } catch (e: Exception) {
        text
    }
}