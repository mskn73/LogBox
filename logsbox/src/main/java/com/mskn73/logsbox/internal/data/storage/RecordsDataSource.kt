package com.mskn73.logsbox.internal.data.storage

import com.mskn73.logsbox.DeveloperRecord
import com.mskn73.logsbox.internal.LogsDispatcher
import kotlinx.coroutines.withContext

class RecordsDataSource(private val database: RecordsDatabase) {

    suspend fun getAllByType(type: String): List<DeveloperRecord> = withContext(LogsDispatcher.io) {
        database.recordDao().getAllByType(type).toDomain()
    }

    suspend fun save(developerRecord: DeveloperRecord) = withContext(LogsDispatcher.io) {
        database.recordDao().insert(
            with(developerRecord) {
                DeveloperRecordEntity(title, type, request, response, timeMillis)
            }
        )
    }
}