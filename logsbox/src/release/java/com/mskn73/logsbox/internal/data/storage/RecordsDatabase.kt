package com.mskn73.logsbox.internal.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(LogEntity::class), version = 1)
@TypeConverters(Converters::class)
internal abstract class RecordsDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordsDao
}