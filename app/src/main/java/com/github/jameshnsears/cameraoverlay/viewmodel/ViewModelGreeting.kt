package com.github.jameshnsears.cameraoverlay.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelGreeting : ViewModel() {
    private val _n = mutableStateOf(0)
    val n: State<Int> = _n

    fun increment() {
        viewModelScope.launch {
            _n.value = _n.value.plus(1)
        }
    }
}
