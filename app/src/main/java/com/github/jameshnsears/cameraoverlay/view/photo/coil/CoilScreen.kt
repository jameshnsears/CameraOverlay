package com.github.jameshnsears.cameraoverlay.view.photo.coil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.photo.mediastore.MediaStoreMediator

@Composable
fun CoilScreen(model: Any?) {
    /*
    vertical list of cards with space between them
    image | Date Taken | GPS
    ...
    image | Date Taken | GPS

    ----------

https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images
v.
https://material.io/components/cards

    card:
    image   Date Taken
    image   GPS
    image   <Path?

     */

    Column {
        AsyncImage(
            model = model,
            contentDescription = null
        )
    }

    /*
Row(
    modifier = Modifier.padding(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {

    // https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://example.com/image.jpg")
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.placeholder),
        contentDescription = stringResource(R.string.description),
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(CircleShape)
    )
}
     */
}

@Preview(name = "Light Theme")
@Composable
fun PreviewCoilScreen() {
    // TODO card's

    Image(
        painterResource(R.drawable.cat),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )

//    CardPhoto()
}
