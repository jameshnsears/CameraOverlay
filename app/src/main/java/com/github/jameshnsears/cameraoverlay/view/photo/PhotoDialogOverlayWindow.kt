package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.jameshnsears.cameraoverlay.R

@Composable
fun PhotoDialogOverlayWindow(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_dialog_overlay),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            PhotoDialogOverlayWindowRow()
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
fun PhotoDialogOverlayWindowRow() {
    Text(
        stringResource(R.string.select_photo_dialog_overlay_colour),
    )
}

@Preview
@Composable
fun PreviewPhotoDialogOverlayWindow() {
    PhotoDialogOverlayWindowRow()
}
