package com.github.jameshnsears.cameraoverlay.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GreetingViewModel : ViewModel() {
    // TODO a reference to a Model object with the business logic

    private val _n = mutableStateOf(0)
    val n: State<Int> = _n

    fun increment() {
        _n.value = _n.value.plus(1)
    }
}
