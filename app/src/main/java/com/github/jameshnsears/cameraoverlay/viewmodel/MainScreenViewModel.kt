package com.github.jameshnsears.cameraoverlay.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediator
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionMediatorImpl
import com.github.jameshnsears.cameraoverlay.stateholder.HelloStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val permissionDataStore = PermissionMediatorImpl(application.applicationContext)

    private val _countDenyAccessPhotos = MutableStateFlow(countDenyAccessPhotos())
    val countDenyAccessPhotos: StateFlow<Int> = _countDenyAccessPhotos

    var helloStateHolder = mutableStateOf(HelloStateHolder(""))
        private set

    init {
        viewModelScope.launch {
            // TODO call a HelloRepository method - retrieve from storage, then set observer
            helloStateHolder.value = HelloStateHolder("pop")
        }
    }

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

    fun onNameChange(newHelloStateHolder: HelloStateHolder) {
        Timber.d(newHelloStateHolder.name)

        // TODO call a HelloRepository method - save to storage, then set observer
        helloStateHolder.value = newHelloStateHolder
    }
}
