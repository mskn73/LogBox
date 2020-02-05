package com.mskn73.logsbox.internal.data.storage

import com.mskn73.logsbox.internal.LogsDispatcher
import com.mskn73.logsbox.internal.domain.Log
import kotlinx.coroutines.withContext

internal class LogsDataSource(private val database: LogsDatabase) {

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
                    requestBody = requestBody,
                    responseHeaders = responseHeaders,
                    responseBody = responseBody,
                    timeMillis = timeMillis,
                    responseTime = responseTime
                )
            }
        )
        database.logsDao().deleteLastElements(20)
    }
}