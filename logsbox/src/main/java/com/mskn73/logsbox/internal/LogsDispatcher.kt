package com.mskn73.logsbox.internal

import kotlinx.coroutines.Dispatchers

object LogsDispatcher {
    val main = Dispatchers.Main
    val io = Dispatchers.IO
}