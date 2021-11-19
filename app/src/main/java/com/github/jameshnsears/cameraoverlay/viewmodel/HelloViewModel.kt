package com.github.jameshnsears.cameraoverlay.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoRepositoryImpl
import com.github.jameshnsears.cameraoverlay.stateholder.HelloStateHolder
import kotlinx.coroutines.launch
import timber.log.Timber

class HelloViewModel : ViewModel() {
    // https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff649690(v=pandp.10)?redirectedfrom=MSDN
    val helloRepository = PhotoRepositoryImpl()

    var helloStateHolder = mutableStateOf(HelloStateHolder(""))
        private set

    init {
        viewModelScope.launch {
            // TODO call a HelloRepository method - retrieve from storage, then set observer
            helloStateHolder.value = HelloStateHolder("pop")
        }
    }

    fun onNameChange(newHelloStateHolder: HelloStateHolder) {
        Timber.d(newHelloStateHolder.name)

        // TODO call a HelloRepository method - save to storage, then set observer
        helloStateHolder.value = newHelloStateHolder
    }
}
