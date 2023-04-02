package com.github.jameshnsears.cameraoverlay.viewmodel

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class GreetingViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    class MainDispatcherRule(
        private val dispatcher: TestDispatcher = StandardTestDispatcher()
    ) : TestWatcher() {
        override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)
        override fun finished(description: Description?) = Dispatchers.resetMain()
    }

    @Test
    fun increment() { // use = runTest if ViewModel uses coroutine
        val viewModel = GreetingViewModel()

        assertEquals(0, viewModel.n.value)

        viewModel.increment()

        assertEquals(1, viewModel.n.value)
    }
}
