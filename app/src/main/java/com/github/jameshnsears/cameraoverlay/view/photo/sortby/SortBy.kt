package com.github.jameshnsears.cameraoverlay.view.photo.sortby

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.North
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.South
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jameshnsears.cameraoverlay.R

@Preview
@Composable
fun SortBy() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
    ) {
        Column {
            Header()
            WhenTaken()
            DistanceFromCurrentLocation()
        }
    }
}

@Composable
private fun Header() {
    Text(
        stringResource(R.string.select_photo_screen_filter_sort_by),
        modifier = Modifier.padding(bottom = 8.dp),
    )
    Divider(
        modifier = Modifier.padding(bottom = 16.dp),
    )
}

@Composable
private fun WhenTaken() {
    Row {
        Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(bottom = 8.dp),
        )
        Text(
            stringResource(R.string.select_photo_screen_when_taken),
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
                .padding(start = 16.dp)
                .padding(bottom = 8.dp),
        )
        Text(
            stringResource(R.string.select_photo_screen_distance),
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
