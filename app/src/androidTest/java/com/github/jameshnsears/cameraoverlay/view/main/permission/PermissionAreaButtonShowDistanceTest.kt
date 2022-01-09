package com.github.jameshnsears.cameraoverlay.view.main.permission

import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.viewmodel.main.ViewModelMainScreen
import org.junit.Before
import org.junit.Test

class PermissionAreaButtonShowDistanceTest : PermissionButtonTest() {
    private lateinit var showDistanceButtonText: String

    @Before
    fun before() {
        if (BuildConfig.GITHUB_ACTION) {
            return
        }

        composeTestRule.setContent {
            PermissionButtonLocation(ViewModelMainScreen(composeTestRule.activity.application))
        }

        showDistanceButtonText = context.resources.getString(R.string.main_show_distance)
    }

    @Test
    fun allow() {
        allowPermission(showDistanceButtonText)
    }
}