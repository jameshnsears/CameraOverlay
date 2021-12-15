package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule

class AccessPhotosPermissionButtonTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val composeTestRule = createComposeRule()

    /*
    @ExperimentalMaterial3Api
    @Test
    fun thatButtonIsClickableTest() {
        composeTestRule.setContent {
            MainScreen(NavController(context = context), HelloViewModel())
        }
        composeTestRule.onNodeWithText(stringResource()).assertIsDisplayed()
        composeTestRule.onNodeWithText(stringResource()).performClick()
    }

    private fun stringResource(): String {
        return context.resources.getString(R.string.main_access_photos)
    }
     */

    /*
    press button
    press allow
    =
    button should be disabled
    */

    /*
    press button
    press deny
    press button
    press deny
    press button
    press back
    press button
    press permissions
    press Files and Media
    Press Allow access to media only
    Press back
    Press back
    press back
    =
    button should be disabled
     */
}