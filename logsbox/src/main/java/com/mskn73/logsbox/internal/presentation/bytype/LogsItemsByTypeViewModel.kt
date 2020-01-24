package com.mskn73.logsbox.internal.presentation.bytype

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskn73.logsbox.DeveloperRecord
import com.mskn73.logsbox.internal.domain.GetRecordsByType
import kotlinx.coroutines.launch

internal class LogsItemsByTypeViewModel(private val getRecordsByType: GetRecordsByType) :
    ViewModel() {

    private val _logs = MutableLiveData<List<DeveloperRecord>>()
    val logs: LiveData<List<DeveloperRecord>>
        get() = _logs

    fun onCreate() {
    }

    fun loadTypes(logType: String) {
        viewModelScope.launch {
            _logs.postValue(getRecordsByType(logType))
        }
    }
}