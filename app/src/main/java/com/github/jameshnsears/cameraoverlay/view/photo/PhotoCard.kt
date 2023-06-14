package com.github.jameshnsears.cameraoverlay.view.photo

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.overlay.OverlayService
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollectionEnum
import com.github.jameshnsears.cameraoverlay.model.photo.card.PhotoCardData
import com.github.jameshnsears.cameraoverlay.view.overlay.window.canDrawOverlays
import com.github.jameshnsears.cameraoverlay.view.overlay.window.showToast

@Composable
fun PhotoCard(navController: NavController, photoCardData: PhotoCardData) {
//    val navigationEndpoint = Navigation.SCREEN_CONFIGURE_OVERLAY + "/${photoCardData.photoId}"

    val context = LocalContext.current

    val missingMandatoryPermissionMessage =
        stringResource(R.string.error_missing_mandatory_permission)

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .height(128.dp)
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                // navController.navigate(navigationEndpoint)

                if (context.canDrawOverlays) {
                    // stop any prior service
                    context.stopService(Intent(context, OverlayService::class.java))

                    // minimise app so that only overlay displayed
                    (context as Activity).moveTaskToBack(true)
                    context.startService(Intent(context, OverlayService::class.java))
                } else {
                    context.showToast(missingMandatoryPermissionMessage)
                }
            }
    ) {
        Column(
            Modifier
                .padding(4.dp)
        ) {
            Row {
                Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                    AsyncImage(
                        model = R.drawable.ic_github_logo,
                        placeholder = painterResource(R.drawable.ic_github_logo),
                        contentDescription = "TODO",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp),
                        contentScale = ContentScale.Crop
                    )

                    /*
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image.uri)
                            .parameters(image.parameters)
                            .build(),
                        placeholder = ColorPainter(Color(image.color)),
                        error = ColorPainter(Color.Red),
                        onSuccess = { placeholder = it.result.memoryCacheKey },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(size)
                            .clickable { screenFlow.value = Screen.Detail(image, placeholder) }
                    )
                     */
                }

                Column(Modifier.padding(start = 16.dp, top = 2.dp)) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        photoCardData.dateTime?.let { Text(it) }
                    }
                    Row(Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.Straighten,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        // TODO convert to distance from 'here'
                        photoCardData.latLong?.let {
                            Text(it.joinToString(),
                                modifier = Modifier.semantics {
                                    this.contentDescription = "GPS EXIF"
                                }
                            )
                        }
                    }
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = null
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//                        Text(photoCardData.type)
                        photoCardData.imageType?.let { Text(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun PreviewPhotoCardData() {
    val photoCardDataLists = listOf(
        PhotoCardData(
            PhotoCollectionEnum.MediaStore,
            "JPEG1",
            "https://example.com/image.jpg",
            "1/1/1970",
            doubleArrayOf(1.0, 2.0)
        ),
        PhotoCardData(
            PhotoCollectionEnum.MediaStore,
            "JPEG2",
            "https://example.com/image.jpg",
            "2/1/1970",
            doubleArrayOf(3.0, 4.0)
        )
    )

    // https://developer.android.com/jetpack/compose/lists
    LazyColumn {
        items(photoCardDataLists) { item ->
            PhotoCard(rememberNavController(), item)
        }
    }
}

@Preview
@Composable
fun PreviewPhotoCardPortrait() {
    PreviewPhotoCardData()
}

@Preview(
    name = "Dark Theme, Landscape",
    widthDp = 720,
    heightDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewPhotoCardLandscape() {
    PreviewPhotoCardData()
}
