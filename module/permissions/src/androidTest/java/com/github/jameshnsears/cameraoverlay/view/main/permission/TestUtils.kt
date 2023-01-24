/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jameshnsears.cameraoverlay.view.main.permission

import android.app.Instrumentation
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector

internal fun <T : ComponentActivity> simulateAppComingFromTheBackground(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<T>, T>
) {
    // Make Activity go through ON_START, and ON_RESUME
    composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.STARTED)
    composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
}

internal fun grantPermissionProgrammatically(
    permission: String,
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
) {
    if (Build.VERSION.SDK_INT < 28) {
        val fileDescriptor = instrumentation.uiAutomation.executeShellCommand(
            "pm grant ${instrumentation.targetContext.packageName} $permission"
        )
        fileDescriptor.checkError()
        fileDescriptor.close()
    } else {
        instrumentation.uiAutomation.grantRuntimePermission(
            instrumentation.targetContext.packageName, permission
        )
    }
}

internal fun pressDrawOverOtherApps(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
) {
    val sdkVersion = Build.VERSION.SDK_INT

    when (sdkVersion) {
        25 -> {
            UiDevice.getInstance(instrumentation).findObject(
                UiSelector().text("Permit drawing over other apps")
            ).click()
        }
        in 30..31 -> {
            UiDevice.getInstance(instrumentation).findObject(
                UiSelector().text("Camera Overlay")
            ).click()

            UiDevice.getInstance(instrumentation).findObject(
                UiSelector().text("Allow display over other apps")
            ).click()
        }
    }

    when (sdkVersion) {
        25 -> pressBack()
        in 30..31 -> {
            pressBack()
            pressBack()
        }
    }
}

internal fun grantPermissionInDialogLocation(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
) {
    UiDevice.getInstance(instrumentation).findPermissionButton(
        when (Build.VERSION.SDK_INT) {
            25 -> "ALLOW"
            29 -> "Allow only while using the app"
            30 -> "WHILE USING THE APP"
            31 -> "While using the app"
            else -> "Allow"
        }
    ).clickForPermission(instrumentation)
}

internal fun grantPermissionInDialogStorage(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
) {
    UiDevice.getInstance(instrumentation).findPermissionButton(
        when (Build.VERSION.SDK_INT) {
            25 -> "ALLOW"
            29 -> "ALLOW"
            30 -> "ALLOW"
            31 -> "Allow"
            else -> "Allow"
        }
    ).clickForPermission(instrumentation)
}

// 25, "ALLOW" / "DENY"
// 31, "While using the app" / "Only this time" / "Don't allow"

internal fun denyPermissionInDialog(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
) {
    UiDevice.getInstance(instrumentation).findPermissionButton(
        when (Build.VERSION.SDK_INT) {
            25 -> "DENY"
            30 -> "DENY"
            31 -> "Don’t allow" // https://en.wikipedia.org/wiki/Right_single_quotation_mark
            else -> "Deny"
        }
    ).clickForPermission(instrumentation)
}

internal fun doNotAskAgainPermissionInDialog(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
) {
    val uiDevice = UiDevice.getInstance(instrumentation)
    when {
        Build.VERSION.SDK_INT == 30 -> {
            denyPermissionInDialog(instrumentation)
        }
        Build.VERSION.SDK_INT > 28 -> {
            uiDevice
                .findPermissionButton("Deny & don’t ask again")
                .clickForPermission(instrumentation)
        }
        Build.VERSION.SDK_INT == 23 -> {
            uiDevice.findObject(
                UiSelector().text("Never ask again")
            ).clickForPermission(instrumentation)
            denyPermissionInDialog(instrumentation)
        }
        else -> {
            uiDevice.findObject(
                UiSelector().text("Don't ask again")
            ).clickForPermission(instrumentation)
            denyPermissionInDialog(instrumentation)
        }
    }
}

internal fun pressBack(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
) {
    val uiDevice = UiDevice.getInstance(instrumentation)
    uiDevice.pressBack()
}

internal fun pressButton(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation(),
    text: String
) {
    val uiDevice = UiDevice.getInstance(instrumentation)
    val uiObject = uiDevice.findObject(
        UiSelector().text(text)
    )
    uiObject.click()
}

private fun UiDevice.findPermissionButton(text: String): UiObject =
    findObject(
        UiSelector()
            .textMatches(text)
            .clickable(true)
    )

private fun UiObject.clickForPermission(
    instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
): Boolean {
    waitUntil { exists() }
    if (!exists()) return false

    val clicked = waitUntil { exists() && click() }
    // Make sure that the tests waits for this click to be processed
    if (clicked) {
        instrumentation.waitForIdleSync()
    }
    return clicked
}

private fun waitUntil(timeoutMillis: Long = 2_000, condition: () -> Boolean): Boolean {
    val startTime = System.nanoTime()
    while (true) {
        if (condition()) return true
        // Let Android run measure, draw and in general any other async operations.
        Thread.sleep(10)
        if (System.nanoTime() - startTime > timeoutMillis * 1_000_000) {
            return false
        }
    }
}
