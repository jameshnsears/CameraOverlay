package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.view.common.Navigation
import com.github.jameshnsears.cameraoverlay.view.photo.menu.collection.CollectionDialog
import com.github.jameshnsears.cameraoverlay.view.photo.menu.filter.FilterDialog
import com.github.jameshnsears.cameraoverlay.view.photo.menu.sortby.SortDialog
import com.github.jameshnsears.cameraoverlay.view.theme.CameraOverlayTheme

@Composable
fun PhotoSelectScreen(navController: NavController) {

    val filterDialogState = remember { mutableStateOf(false) }
    if (filterDialogState.value) {
        FilterDialog(filterDialogState)
    }

    val collectionDialogState = remember { mutableStateOf(false) }
    if (collectionDialogState.value) {
        CollectionDialog(collectionDialogState)
    }

    val sortDialogState = remember { mutableStateOf(false) }
    if (sortDialogState.value) {
        SortDialog(sortDialogState)
    }

    CameraOverlayTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.select_photo)) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Navigation.SCREEN_MAIN) }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { filterDialogState.value = true }) {
                            Icon(
                                imageVector = Icons.Outlined.FilterAlt,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { collectionDialogState.value = true }) {
                            Icon(
                                imageVector = Icons.Outlined.Collections,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = { sortDialogState.value = true }) {
                            Icon(
                                imageVector = Icons.Outlined.Sort,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                )

            },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(Modifier.padding(top=5.dp, bottom=5.dp)) {
                    Text(stringResource(R.string.select_photo_usage_0))
                }

                // TODO replace with coil
                CardPhoto()
            }
        }
    }
}


@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
    PhotoSelectScreen(rememberNavController())
}
