package com.github.jameshnsears.cameraoverlay.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jameshnsears.cameraoverlay.model.main.permission.PermissionMediator
import com.github.jameshnsears.cameraoverlay.model.main.permission.PermissionMediatorImpl
import com.github.jameshnsears.cameraoverlay.stateholder.HelloStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainScreenViewModel(context: Context) : ViewModel() {
    private val permissionDataStore = PermissionMediatorImpl(context.applicationContext)

    private val _countDenyAccessPhotos = MutableStateFlow(countDenyAccessPhotos())
    val countDenyAccessPhotos: StateFlow<Int> = _countDenyAccessPhotos

    private fun countDenyAccessPhotos(): Int {
        var count = 0

        viewModelScope.launch {
            permissionDataStore.countDeny(PermissionMediator.Permission.ACCESS_PHOTOS).collect { countDeny ->
                count = countDeny
            }
        }

        return count
    }

    fun isPermissionGrantedAccessPhotos(): Boolean {
        return permissionDataStore.isAllow(PermissionMediator.Permission.ACCESS_PHOTOS)
    }

    fun denyPermissionAccessPhotos() {
        viewModelScope.launch {
            permissionDataStore.deny(PermissionMediator.Permission.ACCESS_PHOTOS)
            _countDenyAccessPhotos.emit(countDenyAccessPhotos())
        }
    }

    /////////////////////
    // https://developer.android.com/topic/libraries/architecture/coroutines

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
