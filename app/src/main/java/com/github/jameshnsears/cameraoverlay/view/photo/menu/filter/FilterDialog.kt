package com.github.jameshnsears.cameraoverlay.view.photo.menu.filter

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.jameshnsears.cameraoverlay.R

@Composable
fun FilterDialog(openDialog: MutableState<Boolean>) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_dialog_filter),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            FilterDialogRow(context)
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
fun FilterDialogRow(context: Context) {
    Column(
        Modifier
            .padding(top = 5.dp)
    ) {

        Row {
            Text(stringResource(R.string.select_photo_dialog_filter_exif))
        }

        Row(
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* ... */ },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(100.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    Icons.Outlined.Done,
                    contentDescription = "",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.select_photo_dialog_filter_jpeg))
            }

            Button(
                onClick = { /* ... */ },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(100.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.select_photo_dialog_filter_png))
            }

            Button(
                onClick = { /* ... */ },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(100.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.select_photo_dialog_filter_webp))
            }
        }
    }
}

@Preview()
@Composable
fun Preview() {
    val context = LocalContext.current
    FilterDialogRow(context)
}
