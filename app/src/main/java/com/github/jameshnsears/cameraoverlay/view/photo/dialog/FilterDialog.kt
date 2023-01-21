package com.github.jameshnsears.cameraoverlay.view.photo.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.jameshnsears.cameraoverlay.R

@Composable
fun PhotoDialogFilter(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
            // TODO viewmodel?
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_filter),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            FilterDialogRow()
        },
        confirmButton = {},
        dismissButton = {},
    )
}

@Composable
fun FilterDialogRow() {
    val jpeg = stringResource(R.string.select_photo_dialog_filter_jpeg)
    var selected by remember { mutableStateOf(jpeg) }

    val checkboxGroupOptions = listOf(
        jpeg,
        stringResource(R.string.select_photo_dialog_filter_png),
        stringResource(R.string.select_photo_dialog_filter_webp),
    )

    Column {
        val onSelectedChange = { text: String ->
            selected = text
        }

        checkboxGroupOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selected),
                        onClick = { onSelectedChange(text) },
                    ),
            ) {
                Checkbox(
                    checked = (text == selected),
                    onCheckedChange = { onSelectedChange(text) },
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}

@Preview
@Composable
fun PreviewPhotoDialogFilter() {
    FilterDialogRow()
}
