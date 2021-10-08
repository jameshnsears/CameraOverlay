package com.github.jameshnsears.picturepostcard.view.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jameshnsears.picturepostcard.R

@Preview
@Composable
fun CardPhoto() {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .width(225.dp)
            .height(225.dp)
    ) {
        Column(
            Modifier
                .padding(4.dp)
        ) {
            Row(modifier = Modifier
                .height(170.dp)
                .width(225.dp)) {
                Image(
                    painterResource(R.drawable.cat),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
            Row {
                Divider()
            }
            Row {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                )
                Text(
                    "YYYYMMDD, HHMMSS",
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
            Row {
                Divider()
            }
            Row {
                Icon(
                    imageVector = Icons.Outlined.Straighten,
                    contentDescription = null,
                )
                Text(
                    "0m",
                    modifier = Modifier.padding(start = 16.dp)
                )
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Navigation,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
