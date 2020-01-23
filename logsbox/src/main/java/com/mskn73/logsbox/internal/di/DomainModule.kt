package com.mskn73.logsbox.internal.di

import com.mskn73.logsbox.internal.domain.AddRecord
import com.mskn73.logsbox.internal.domain.GetRecordsByType

internal class DomainModule(private val databaseModule: DatabaseModule) {
    fun provideAddRecord(): AddRecord =
        AddRecord(
            databaseModule.providesRepository()
        )

    fun providesGetRecordsByType(): GetRecordsByType =
        GetRecordsByType(
            databaseModule.providesRepository()
        )
}