package com.github.jameshnsears.cameraoverlay.view

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavController
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.view.main.MainScreen
import com.github.jameshnsears.cameraoverlay.viewmodel.HelloViewModel
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @ExperimentalMaterial3Api
    @Test
    fun thatButtonIsClickableTest() {
        // TODO:
        // use very specific, detailed names, for tests
        // make tests very short by moving code into fun's, so test code + fun names are very readable
        // Arrange; Act; Assert

        composeTestRule.setContent {
            MainScreen(NavController(context = context), HelloViewModel())
        }

        composeTestRule.onRoot().printToLog("XXXXXX")

        composeTestRule.onNodeWithText(stringResource()).assertIsDisplayed()
        composeTestRule.onNodeWithText(stringResource()).performClick()
    }

    private fun stringResource(): String {
        return context.resources.getString(R.string.main_media)
    }
}
