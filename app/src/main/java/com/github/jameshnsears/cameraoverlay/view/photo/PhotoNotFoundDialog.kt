package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.jameshnsears.cameraoverlay.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Composable
fun PhotoNotFoundDialog() {
    val context = LocalContext.current

    MaterialAlertDialogBuilder(context)
        .setTitle(stringResource(R.string.select_photo_location_error_dialog_message))
        .setMessage(stringResource(R.string.select_photo_location_error_dialog_message))
        .setNeutralButton(stringResource(R.string.select_photo_location_error_dialog_cancel)) {
            _, _ ->
            // Respond to neutral button press
        }
        .show()
}
