package com.mskn73.logsbox.internal.data

import com.mskn73.logsbox.internal.data.storage.LogsDataSource
import com.mskn73.logsbox.internal.domain.Log
import com.mskn73.logsbox.internal.domain.LogsRepository

internal class LogsDataRepository(
    private val logsDataSource: LogsDataSource
) : LogsRepository {

    override suspend fun getTypes(): List<String> = logsDataSource.getTypes()

    override suspend fun getAllByType(type: String): List<Log> =
        logsDataSource.getAllByType(type)

    override suspend fun save(log: Log) =
        logsDataSource.save(log)
}