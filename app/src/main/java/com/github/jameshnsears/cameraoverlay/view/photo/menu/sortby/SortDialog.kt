package com.github.jameshnsears.cameraoverlay.view.photo.menu.sortby

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.North
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.South
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.jameshnsears.cameraoverlay.R

@Preview
@Composable
fun Preview() {
    SortDialogRow()
}

@Composable
fun SortDialog(openDialog: MutableState<Boolean>) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_dialog_sort),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            SortDialogRow()
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
fun SortDialogRow() {
    Column {
        WhenTaken()
        DistanceFromCurrentLocation()
    }
}

@Composable
private fun WhenTaken() {
    Row {
        Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 8.dp),
        )
        Text(
            stringResource(R.string.select_photo_dialog_sort_when_taken),
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(bottom = 8.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.North,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun DistanceFromCurrentLocation() {
    Row {
        Icon(
            imageVector = Icons.Outlined.Straighten,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 8.dp),
        )
        Text(
            stringResource(R.string.select_photo_dialog_sort_distance),
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(bottom = 8.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.South,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}
