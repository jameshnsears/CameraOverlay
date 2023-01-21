package com.github.jameshnsears.cameraoverlay.view.photo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCardData
import com.github.jameshnsears.cameraoverlay.model.photo.PhotoCollection
import com.github.jameshnsears.cameraoverlay.view.common.CommonNavigation
import com.github.jameshnsears.cameraoverlay.view.photo.dialog.PhotoDialogCollection
import com.github.jameshnsears.cameraoverlay.view.photo.dialog.PhotoDialogFilter
import com.github.jameshnsears.cameraoverlay.view.photo.dialog.PhotoDialogSort
import com.github.jameshnsears.cameraoverlay.view.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSelectScreen(navController: NavController) {
    AppTheme {
        val filterDialogState = remember { mutableStateOf(false) }
        if (filterDialogState.value) {
            PhotoDialogFilter(filterDialogState)
        }

        val collectionDialogState = remember { mutableStateOf(false) }
        if (collectionDialogState.value) {
            PhotoDialogCollection(collectionDialogState)
        }

        val sortDialogState = remember { mutableStateOf(false) }
        if (sortDialogState.value) {
            PhotoDialogSort(sortDialogState)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(CommonNavigation.SCREEN_MAIN) }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        TopBarActions(
                            filterDialogState,
                            collectionDialogState,
                            sortDialogState,
                        )
                    },
                )
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
            ) {
                for (photoId in 1..20) {
                    PhotoCard(
                        navController,
                        PhotoCardData(
                            PhotoCollection.MediaStore,
                            "type $photoId",
                            "https://example.com/image.jpg",
                            "when taken $photoId",
                            "distance $photoId",
                            photoId = photoId,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarActions(
    filterDialogState: MutableState<Boolean>,
    collectionDialogState: MutableState<Boolean>,
    sortDialogState: MutableState<Boolean>,
) {
    IconButton(onClick = { collectionDialogState.value = true }) {
        Icon(
            imageVector = Icons.Outlined.Collections,
            contentDescription = stringResource(R.string.select_photo_collections),
        )
    }

    IconButton(onClick = { filterDialogState.value = true }) {
        Icon(
            imageVector = Icons.Outlined.FilterAlt,
            contentDescription = stringResource(R.string.select_photo_filter),
        )
    }

    IconButton(onClick = { sortDialogState.value = true }) {
        Icon(
            imageVector = Icons.Outlined.Sort,
            contentDescription = stringResource(R.string.select_photo_sort),
        )
    }
}

@Preview(name = "Light Theme")
@Composable
fun PreviewPhotoSelectScreen() {
    PhotoSelectScreen(rememberNavController())
}
