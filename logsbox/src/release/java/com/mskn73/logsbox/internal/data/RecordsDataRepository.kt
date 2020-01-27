package com.mskn73.logsbox.internal.data

import com.mskn73.logsbox.internal.data.storage.RecordsDataSource
import com.mskn73.logsbox.internal.domain.DeveloperRecord
import com.mskn73.logsbox.internal.domain.RecordsRepository

internal class RecordsDataRepository(
    private val recordsDataSource: RecordsDataSource
) : RecordsRepository {

    override suspend fun getTypes(): List<String> = recordsDataSource.getTypes()

    override suspend fun getAllByType(type: String): List<DeveloperRecord> =
        recordsDataSource.getAllByType(type)

    override suspend fun save(record: DeveloperRecord) =
        recordsDataSource.save(record)
}