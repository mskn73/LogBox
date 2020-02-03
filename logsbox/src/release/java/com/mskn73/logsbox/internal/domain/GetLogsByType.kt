package com.mskn73.logsbox.internal.domain

internal class GetLogsByType(private val repository: LogsRepository) {

    suspend operator fun invoke(type: String): List<Log> =
        repository.getAllByType(type)
}