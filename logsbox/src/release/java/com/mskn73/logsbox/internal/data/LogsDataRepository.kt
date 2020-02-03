package com.mskn73.logsbox.internal.data

import com.mskn73.logsbox.internal.data.storage.RecordsDataSource
import com.mskn73.logsbox.internal.domain.Log
import com.mskn73.logsbox.internal.domain.LogsRepository

internal class LogsDataRepository(
    private val recordsDataSource: RecordsDataSource
) : LogsRepository {

    override suspend fun getTypes(): List<String> = recordsDataSource.getTypes()

    override suspend fun getAllByType(type: String): List<Log> =
        recordsDataSource.getAllByType(type)

    override suspend fun save(log: Log) =
        recordsDataSource.save(log)
}