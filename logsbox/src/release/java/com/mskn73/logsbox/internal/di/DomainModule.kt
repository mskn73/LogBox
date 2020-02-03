package com.mskn73.logsbox.internal.di

import com.mskn73.logsbox.internal.domain.AddRecord
import com.mskn73.logsbox.internal.domain.GetRecordTypes
import com.mskn73.logsbox.internal.domain.GetLogsByType

internal class DomainModule(private val databaseModule: DatabaseModule) {
    fun provideAddRecord(): AddRecord =
        AddRecord(
            databaseModule.providesRepository()
        )

    fun providesGetRecordsByType(): GetLogsByType =
        GetLogsByType(
            databaseModule.providesRepository()
        )

    fun providesGetTypes(): GetRecordTypes =
        GetRecordTypes(
            databaseModule.providesRepository()
        )
}