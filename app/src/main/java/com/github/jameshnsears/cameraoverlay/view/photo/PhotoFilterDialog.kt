package com.github.jameshnsears.cameraoverlay.view.photo

import ButtonOk
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.jameshnsears.cameraoverlay.R

@Composable
fun FilterDialog(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
            // TODO viewmodel?
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_filter),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            FilterDialogRow()
        },
        confirmButton = {},
        dismissButton = {
            ButtonOk { openDialog.value = false }
        }
    )
}

@Composable
fun FilterDialogRow() {
    var state by remember { mutableStateOf(true) }

    Column(
        Modifier
            .selectableGroup()
    ) {
        Row {
            Text(stringResource(R.string.select_photo_dialog_filter_exif))
        }

        Row(
            Modifier
                .padding(top = 20.dp, start = 16.dp, bottom = 15.dp)
        ) {
            RadioButton(
                selected = state,
                onClick = { state = true },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary,
                    unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.select_photo_dialog_filter_jpeg))
        }

        Row(
            Modifier
                .padding(top = 10.dp, start = 16.dp, bottom = 15.dp)
        ) {
            RadioButton(
                selected = !state,
                onClick = { state = false },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary,
                    unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.select_photo_dialog_filter_png))
        }

        Row(
            Modifier
                .padding(top = 10.dp, start = 16.dp)
        ) {
            RadioButton(
                selected = !state,
                onClick = { state = false },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary,
                    unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.select_photo_dialog_filter_webp))
        }
    }
}

@Preview
@Composable
fun PreviewPhotoFilterDialog() {
    FilterDialogRow()
}
