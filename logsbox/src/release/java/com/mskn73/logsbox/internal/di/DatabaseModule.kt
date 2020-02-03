package com.mskn73.logsbox.internal.di

import android.content.Context
import androidx.room.Room
import com.mskn73.logsbox.internal.data.LogsDataRepository
import com.mskn73.logsbox.internal.data.storage.LogsDataSource
import com.mskn73.logsbox.internal.data.storage.LogsDatabase
import com.mskn73.logsbox.internal.domain.LogsRepository

internal class DatabaseModule(
    private val applicationContext: Context,
    private val databaseName: String = "logs-database"
) {

    private fun providesDatabase(): LogsDatabase =
        Room.databaseBuilder(applicationContext, LogsDatabase::class.java, databaseName).build()

    private fun providesLogsDataSource(): LogsDataSource = LogsDataSource(
        providesDatabase()
    )

    fun providesRepository(): LogsRepository = LogsDataRepository(
        providesLogsDataSource()
    )
}