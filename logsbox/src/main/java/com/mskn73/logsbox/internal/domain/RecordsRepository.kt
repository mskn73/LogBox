package com.mskn73.logsbox.internal.domain

import com.mskn73.logsbox.DeveloperRecord

internal interface RecordsRepository {

    suspend fun getTypes(): List<String>

    suspend fun getAllByType(type: String): List<DeveloperRecord>

    suspend fun save(record: DeveloperRecord)
}