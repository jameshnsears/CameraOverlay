package com.github.jameshnsears.picturepostcard.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jameshnsears.picturepostcard.model.hello.HelloRepository
import com.github.jameshnsears.picturepostcard.stateholder.HelloStateHolder
import kotlinx.coroutines.launch
import timber.log.Timber

class HelloViewModel: ViewModel() {
    // https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff649690(v=pandp.10)?redirectedfrom=MSDN
    val helloRepository = HelloRepository()

    // https://developer.android.com/codelabs/jetpack-compose-state?index=..%2F..index#8
    // If this ViewModel was also used by the View system, it would be better to continue using LiveData
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