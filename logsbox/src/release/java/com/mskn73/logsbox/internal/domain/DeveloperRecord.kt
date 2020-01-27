package com.mskn73.logsbox.internal.domain

import java.io.Serializable

internal data class DeveloperRecord(
    val title: String,
    val type: String,
    val request: String,
    val response: String,
    val timeMillis: Long = System.currentTimeMillis()
) : Serializable {
    companion object {
        const val KEY = "DeveloperRecord"
    }
}