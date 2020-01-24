package com.mskn73.logsbox.internal.presentation.logslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mskn73.logsbox.internal.di.LogsMainModule

class LogsBoxViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val mainModule = LogsMainModule(context)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogsBoxViewModel::class.java)) {
            return LogsBoxViewModel(
                mainModule.providesGetTypes()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}