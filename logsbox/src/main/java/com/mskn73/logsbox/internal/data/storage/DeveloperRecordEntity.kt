package com.mskn73.logsbox.internal.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mskn73.logsbox.DeveloperRecord

@Entity(tableName = "records")
data class DeveloperRecordEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "request") val request: String,
    @ColumnInfo(name = "response") val response: String,
    @ColumnInfo(name = "timeMillis") val timeMillis: Long = System.currentTimeMillis()
) {

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0

    fun toDomain(): DeveloperRecord = DeveloperRecord(title, type, request, response, timeMillis)
}

fun List<DeveloperRecordEntity>.toDomain(): List<DeveloperRecord> = map { it.toDomain() }