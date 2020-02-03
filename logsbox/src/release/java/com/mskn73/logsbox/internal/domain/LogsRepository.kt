package com.mskn73.logsbox.internal.domain

internal interface LogsRepository {

    suspend fun getTypes(): List<String>

    suspend fun getAllByType(type: String): List<Log>

    suspend fun save(log: Log)
}