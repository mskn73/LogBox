package com.mskn73.logsbox.internal.domain

import java.io.Serializable

internal data class Log(
    val title: String,
    val type: String,
    val requestHeaders: List<String>,
    val requestBody: String,
    val responseHeaders: List<String>,
    val responseBody: String,
    val responseTime: Long = -1,
    val timeMillis: Long = System.currentTimeMillis()
) : Serializable {
    companion object {
        const val KEY = "log-key"
    }
}