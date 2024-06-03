package com.ajeproduction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData("LiveData")
    val liveData: LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow("StateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun updateLiveData() {
        _liveData.value = "LiveData Updated"
    }

    fun updateStateFlow() {
        viewModelScope.launch {
            _stateFlow.emit("StateFlow Updated")
        }
    }

    fun updateFlow(): Flow<String> {
        return flow {
            repeat(5) {
                emit("Flow Updated to $it")
                delay(1000)
            }
        }
    }


    fun updateShareFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow Updated")
        }
    }


}