package com.mskn73.logsbox.internal.di

import android.content.Context
import com.mskn73.logsbox.internal.domain.AddLog
import com.mskn73.logsbox.internal.domain.GetLogsTypes
import com.mskn73.logsbox.internal.domain.GetLogsByType

internal class LogsMainModule(context: Context) {

    private val databaseModule = DatabaseModule(context)
    private val domainModule = DomainModule(databaseModule)

    fun provideAddLog(): AddLog = domainModule.provideAddLog()

    fun providesGetLogsByType(): GetLogsByType = domainModule.providesGetLogsByType()

    fun providesGetTypes(): GetLogsTypes = domainModule.providesGetTypes()
}