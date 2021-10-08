package com.github.jameshnsears.picturepostcard.view.photo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.picturepostcard.R
import com.github.jameshnsears.picturepostcard.view.Navigation
import com.github.jameshnsears.picturepostcard.view.common.CommonSmallTopAppBar
import com.github.jameshnsears.picturepostcard.view.photo.sortby.SortByDialog
import com.github.jameshnsears.picturepostcard.view.theme.PicturePostcardTheme

@ExperimentalMaterial3Api
@Composable
fun PhotoScreen(navController: NavController) {
    PicturePostcardTheme {
        Scaffold(
            topBar = {
                CommonSmallTopAppBar(
                    stringResource(R.string.select_photo),
                    navController,
                    Navigation.MAIN_SCREEN
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(start=16.dp, end=16.dp).fillMaxWidth()
            ) {
                Location()

                SortByDialog()

                CardPhoto()

                ConfigureOverlay(navController)
            }
        }
    }
}

@Composable
fun Location() {
    Column {
        ElevatedButton(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.FolderOpen,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.select_photo_location)
            )
        }
    }
}

@Composable
fun ConfigureOverlay(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        ElevatedButton(
            onClick = { navController.navigate(Navigation.OVERLAY_SCREEN) },
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = false
        ) {
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.overlay_configure_selection)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
    PhotoScreen(rememberNavController())
}
