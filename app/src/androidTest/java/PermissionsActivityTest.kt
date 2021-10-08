import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.github.jameshnsears.picturepostcard.R
import org.junit.Rule
import org.junit.Test


class PermissionsActivityTestTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    // https://developer.android.com/jetpack/compose/testing
    // https://developer.android.com/jetpack/compose/testing-cheatsheet

    @Test
    fun buttonTest() {
        composeTestRule.setContent {
            MainScreen()
        }

        composeTestRule.onRoot().printToLog("XXXXXX")

        composeTestRule.onNodeWithText(stringResource()).assertIsDisplayed()
        composeTestRule.onNodeWithText(stringResource()).performClick()
    }

    private fun stringResource(): String {
        // at time of writing, compose androidTest not able to call actual stringResource avail. in main!
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.resources.getString(R.string.permissions_media)
    }
}
