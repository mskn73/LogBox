package com.mskn73.logsbox.internal.domain

import com.mskn73.logsbox.DeveloperRecord

internal class AddRecord(private val repository: RecordsRepository) {
    suspend operator fun invoke(record: DeveloperRecord) =
        repository.save(record)
}