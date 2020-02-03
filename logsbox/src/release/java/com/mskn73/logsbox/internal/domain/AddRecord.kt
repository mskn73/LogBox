package com.mskn73.logsbox.internal.domain

internal class AddRecord(private val repository: LogsRepository) {
    suspend operator fun invoke(record: Log) =
        repository.save(record)
}