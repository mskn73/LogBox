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
        request: String,
        response: String
    ) {
        // no-op
    }
}