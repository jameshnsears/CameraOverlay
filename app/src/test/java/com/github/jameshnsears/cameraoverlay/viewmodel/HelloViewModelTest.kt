package com.github.jameshnsears.cameraoverlay.viewmodel

import com.github.jameshnsears.cameraoverlay.stateholder.HelloStateHolder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class HelloViewModelTest {
    @Test
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    fun onNameChange() = runBlockingTest {
        val helloViewModel = HelloViewModel()
        helloViewModel.onNameChange(HelloStateHolder("a"))

        assertThat(helloViewModel.helloStateHolder.value.name).isEqualTo("a")
    }
}
