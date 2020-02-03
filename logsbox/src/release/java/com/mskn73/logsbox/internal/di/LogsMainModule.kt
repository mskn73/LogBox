package com.mskn73.logsbox.internal.di

import android.content.Context
import com.mskn73.logsbox.internal.domain.AddRecord
import com.mskn73.logsbox.internal.domain.GetRecordTypes
import com.mskn73.logsbox.internal.domain.GetLogsByType

internal class LogsMainModule(context: Context) {

    private val databaseModule = DatabaseModule(context)
    private val domainModule = DomainModule(databaseModule)

    fun provideAddRecord(): AddRecord = domainModule.provideAddRecord()

    fun providesGetRecordsByType(): GetLogsByType = domainModule.providesGetRecordsByType()

    fun providesGetTypes(): GetRecordTypes = domainModule.providesGetTypes()
}