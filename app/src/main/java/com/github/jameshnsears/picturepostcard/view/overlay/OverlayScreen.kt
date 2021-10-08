package com.github.jameshnsears.picturepostcard.view.overlay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.picturepostcard.R
import com.github.jameshnsears.picturepostcard.view.Navigation
import com.github.jameshnsears.picturepostcard.view.common.CommonSmallTopAppBar
import com.github.jameshnsears.picturepostcard.view.theme.PicturePostcardTheme

@ExperimentalMaterial3Api
@Composable
fun OverlayScreen(navController: NavController) {
    PicturePostcardTheme {
        Scaffold(
            topBar = {
                CommonSmallTopAppBar(
                    stringResource(R.string.overlay_configure_selection),
                    navController,
                    Navigation.PHOTO_SCREEN
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(start=16.dp, end=16.dp).fillMaxWidth()
            ) {
                Window()

                Photo()

                EdgeDetection()

                DisplayOverlay()
            }
        }
    }
}

@Composable
fun Window() {
    Column(
        modifier = Modifier.padding(bottom = 4.dp),
    ) {
        Text(
            stringResource(R.string.overlay_configure_window),
            modifier = Modifier.padding(bottom = 5.dp),
            fontWeight = FontWeight.Bold,
        )
        Row {
            Icon(
                imageVector = Icons.Outlined.Palette,
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp),
            )
            Text(
                stringResource(R.string.overlay_configure_colour),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_width_black_24dp),
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                stringResource(R.string.overlay_configure_width),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Row {
            Icon(
                imageVector = Icons.Outlined.Height,
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp),
            )
            Text(
                stringResource(R.string.overlay_configure_height),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun Photo() {
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
    ) {
        Text(
            stringResource(R.string.overlay_configure_photo),
            modifier = Modifier.padding(bottom = 5.dp),
            fontWeight = FontWeight.Bold,
        )
        Row {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp),
            )
            Text(
                stringResource(R.string.overlay_configure_transparency),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun EdgeDetection() {
    Column(
    ) {
        Row {
            Text(
                stringResource(R.string.overlay_configure_edge_detection),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(bottom = 5.dp)
                    .padding(top = 4.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_blur_24dp),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    stringResource(R.string.overlay_configure_blur),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_threshold_24dp),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp),
                )
                Text(
                    stringResource(R.string.overlay_configure_thresholds),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DisplayOverlay() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        ElevatedButton(
            onClick = {},
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = false
        ) {
            Icon(
                imageVector = Icons.Outlined.Layers,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.overlay_configure_display_overlay)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
    OverlayScreen(rememberNavController())
}
