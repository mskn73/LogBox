package com.mskn73.logsbox.internal.domain

internal interface RecordsRepository {

    suspend fun getTypes(): List<String>

    suspend fun getAllByType(type: String): List<DeveloperRecord>

    suspend fun save(record: DeveloperRecord)
}