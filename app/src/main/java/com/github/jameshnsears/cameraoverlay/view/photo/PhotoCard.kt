package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.jameshnsears.cameraoverlay.R

@Preview
@Composable
fun CardPhoto() {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .height(128.dp)
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(4.dp)
        ) {
            Row {
//                Image(
//                    painterResource(R.drawable.cat),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(),
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit,
//                )

                /*
lazy list of cards

----------

https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images
v.
https://material.io/components/cards
 */

                Column {
                    // placeholder provides the @Preview image
                    AsyncImage(
                        model = "https://example.com/image.jpg",
                        placeholder = painterResource(R.drawable.ic_github_logo),
                        contentDescription = "contentDescription",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(120.dp),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(Modifier.padding(start = 16.dp)) {
                    Row {
                        Text("Date Taken")
                    }
                    Row(Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                        Text("GPS co-ords")
                    }
                    Row {
                        Text("Path?", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
