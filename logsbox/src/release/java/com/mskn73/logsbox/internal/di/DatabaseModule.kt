package com.mskn73.logsbox.internal.di

import android.content.Context
import androidx.room.Room
import com.mskn73.logsbox.internal.data.LogsDataRepository
import com.mskn73.logsbox.internal.data.storage.RecordsDataSource
import com.mskn73.logsbox.internal.data.storage.RecordsDatabase
import com.mskn73.logsbox.internal.domain.LogsRepository

internal class DatabaseModule(
    private val applicationContext: Context,
    private val databaseName: String = "records-database"
) {

    private fun providesDatabase(): RecordsDatabase =
        Room.databaseBuilder(applicationContext, RecordsDatabase::class.java, databaseName).build()

    private fun providesRecordsDataSource(): RecordsDataSource = RecordsDataSource(
        providesDatabase()
    )

    fun providesRepository(): LogsRepository = LogsDataRepository(
        providesRecordsDataSource()
    )
}