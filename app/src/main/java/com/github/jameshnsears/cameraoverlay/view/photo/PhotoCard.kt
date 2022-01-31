package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jameshnsears.cameraoverlay.R

@Preview
@Composable
fun CardPhoto() {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .height(170.dp)
            .width(225.dp)
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Column(
            Modifier
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(170.dp)
                    .width(225.dp)
            ) {
                Image(
                    painterResource(R.drawable.cat),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}
