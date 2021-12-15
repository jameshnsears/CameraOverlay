package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CommonSmallTopAppBar(title: String, navController: NavController, navRoute: String) {
    SmallTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(navRoute) }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}
