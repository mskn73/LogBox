package com.mskn73.logsbox.internal.data.storage

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.mskn73.logsbox.internal.LogsDispatcher
import com.mskn73.logsbox.internal.domain.DeveloperRecord
import kotlinx.coroutines.withContext

internal class RecordsDataSource(private val database: RecordsDatabase) {
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    suspend fun getTypes(): List<String> = withContext(LogsDispatcher.io) {
        database.recordDao().getTypes()
    }

    suspend fun getAllByType(type: String): List<DeveloperRecord> = withContext(LogsDispatcher.io) {
        database.recordDao().getAllByType(type).toDomain()
    }

    suspend fun save(developerRecord: DeveloperRecord) = withContext(LogsDispatcher.io) {
        database.recordDao().insert(
            with(developerRecord) {
                DeveloperRecordEntity(
                    title = title,
                    type = type,
                    request = formatAsPrettyJson(request),
                    response = formatAsPrettyJson(response),
                    timeMillis = timeMillis
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