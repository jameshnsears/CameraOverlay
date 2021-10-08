package com.github.jameshnsears.picturepostcard.view.photo.sortby

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jameshnsears.picturepostcard.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Composable
@Preview
fun SortByDialog() {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ShowDialog()
    }

    Column (modifier = Modifier.padding(bottom = 10.dp)) {
        ElevatedButton(
            onClick = { showDialog.value = true },
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.select_photo_filter_dialog)
            )
        }
    }
}

@Composable
fun ShowDialog() {
    val context = LocalContext.current

    MaterialAlertDialogBuilder(context)
        .setTitle(stringResource(R.string.select_photo_location_error_dialog_message))
        .setMessage(stringResource(R.string.select_photo_location_error_dialog_message))
        .setNeutralButton(stringResource(R.string.select_photo_location_error_dialog_cancel)) { dialog, which ->
            // Respond to neutral button press
        }
        .show()
}
