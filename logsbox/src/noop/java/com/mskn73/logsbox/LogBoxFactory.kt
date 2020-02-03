package com.mskn73.logsbox

import android.content.Context

object LogBoxFactory {

    fun openLogs(context: Context) {
        // no-op
    }

    fun init(context: Context, databaseName: String = "log-box.db", shakeDetector: Boolean = false) {
        // no-op
    }

    fun log(
        type: String,
        title: String,
        requestHeaders: List<String> = emptyList(),
        request: String,
        responseHeaders: List<String> = emptyList(),
        responseTime: Long = -1,
        response: String
    ) {
        // no-op
    }
}