package com.mskn73.logsbox.internal.domain

internal class AddLog(private val repository: LogsRepository) {
    suspend operator fun invoke(log: Log) =
        repository.save(log)
}