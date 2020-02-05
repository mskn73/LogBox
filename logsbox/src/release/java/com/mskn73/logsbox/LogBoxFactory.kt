package com.mskn73.logsbox

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import androidx.fragment.app.Fragment
import com.mskn73.logsbox.internal.di.LogsMainModule
import com.mskn73.logsbox.internal.domain.AddLog
import com.mskn73.logsbox.internal.domain.Log
import com.mskn73.logsbox.internal.presentation.logslist.LogBoxActivity
import com.mskn73.logsbox.internal.presentation.logslist.LogsBoxFragment
import com.squareup.seismic.ShakeDetector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object LogBoxFactory {
    private var addLog: AddLog? = null
    private var logsMainModule: LogsMainModule? = null

    fun openLogs(context: Context) {
        context.startActivity(
            LogBoxActivity.newIntent(context)
        )
    }

    fun getFragment(): Fragment =
        LogsBoxFragment.newInstance()

    fun init(context: Context, databaseName: String = "log-box.db", shakeDetection: Boolean, maxRows: Int=1000) {
        logsMainModule = LogsMainModule(context)
        addLog = logsMainModule?.provideAddLog()

        if (shakeDetection) {
            initShaker(context)
        }
    }

    fun log(
        type: String,
        title: String,
        requestHeaders: List<String>,
        request: String,
        responseHeaders: List<String>,
        responseTime: Long,
        response: String
    ) {
        addLog?.let { addLog ->
            GlobalScope.launch {
                addLog(
                    Log(
                        title = title,
                        type = type,
                        requestHeaders = requestHeaders,
                        requestBody = request,
                        responseHeaders = responseHeaders,
                        responseBody = response,
                        responseTime = responseTime
                    )
                )
            }
        } ?: throw RuntimeException("LogBox is not initialized")
    }

    private fun initShaker(context: Context) {
        val sensorManager =
            context.getSystemService(SENSOR_SERVICE) as? SensorManager
        sensorManager?.let {
            val sd = ShakeDetector { openLogs(context) }
            sd.start(it)
        }
    }
}