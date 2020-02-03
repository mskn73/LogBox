package com.mskn73.logsbox.internal.di

import com.mskn73.logsbox.internal.domain.AddLog
import com.mskn73.logsbox.internal.domain.GetLogsTypes
import com.mskn73.logsbox.internal.domain.GetLogsByType

internal class DomainModule(private val databaseModule: DatabaseModule) {
    fun provideAddLog(): AddLog =
        AddLog(
            databaseModule.providesRepository()
        )

    fun providesGetLogsByType(): GetLogsByType =
        GetLogsByType(
            databaseModule.providesRepository()
        )

    fun providesGetTypes(): GetLogsTypes =
        GetLogsTypes(
            databaseModule.providesRepository()
        )
}