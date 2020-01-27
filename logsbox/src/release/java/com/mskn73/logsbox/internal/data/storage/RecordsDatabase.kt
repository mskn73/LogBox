package com.mskn73.logsbox.internal.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DeveloperRecordEntity::class), version = 1)
internal abstract class RecordsDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordsDao
}