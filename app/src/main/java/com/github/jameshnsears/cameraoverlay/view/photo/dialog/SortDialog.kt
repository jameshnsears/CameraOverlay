package com.github.jameshnsears.cameraoverlay.view.photo.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.North
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.South
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
fun PhotoDialogSort(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_sort),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            PhotoDialogSortRow()
        },
        confirmButton = {},
        dismissButton = {},
    )
}

@Composable
fun PhotoDialogSortRow() {
    Column(modifier = Modifier.padding(start = 16.dp)) {
        WhenTakenRow()
        Divider()
        DistanceRow()
    }
}

@Composable
private fun WhenTakenRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = null,
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            stringResource(R.string.select_photo_dialog_sort_when_taken),
            modifier = Modifier
                .align(Alignment.CenterVertically),
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
        ) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.North,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun DistanceRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            imageVector = Icons.Outlined.Straighten,
            contentDescription = null,
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            stringResource(R.string.select_photo_dialog_sort_distance),
            modifier = Modifier
                .align(Alignment.CenterVertically),
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
        ) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.South,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPhotoDialogSort() {
    PhotoDialogSortRow()
}
