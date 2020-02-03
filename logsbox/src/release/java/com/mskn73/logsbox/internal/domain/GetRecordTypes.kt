package com.mskn73.logsbox.internal.domain

internal class GetRecordTypes(private val repository: LogsRepository) {

    suspend operator fun invoke(): List<String> =
        repository.getTypes()
}