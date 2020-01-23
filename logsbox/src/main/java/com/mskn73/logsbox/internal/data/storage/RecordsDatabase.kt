package com.mskn73.logsbox.internal.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mskn73.logsbox.internal.data.storage.DeveloperRecordEntity
import com.mskn73.logsbox.internal.data.storage.RecordsDao

@Database(entities = arrayOf(DeveloperRecordEntity::class), version = 1)
abstract class RecordsDatabase : RoomDatabase() {

    abstract fun recordDao() : RecordsDao

}