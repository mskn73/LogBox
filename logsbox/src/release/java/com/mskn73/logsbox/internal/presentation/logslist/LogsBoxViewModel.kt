package com.mskn73.logsbox.internal.presentation.logslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskn73.logsbox.internal.domain.GetRecordTypes
import kotlinx.coroutines.launch

internal class LogsBoxViewModel(private val getRecordTypes: GetRecordTypes) : ViewModel() {

    private val _types = MutableLiveData<List<String>>()
    val types: LiveData<List<String>>
        get() = _types

    fun onCreate() {
        loadTypes()
    }

    private fun loadTypes() {
        viewModelScope.launch {
            _types.postValue(getRecordTypes())
        }
    }
}