package com.mskn73.logsbox.internal.domain

import com.mskn73.logsbox.DeveloperRecord

internal class GetRecordsByType(private val repository: RecordsRepository) {

    suspend operator fun invoke(type: String): List<DeveloperRecord> =
        repository.getAllByType(type)
}