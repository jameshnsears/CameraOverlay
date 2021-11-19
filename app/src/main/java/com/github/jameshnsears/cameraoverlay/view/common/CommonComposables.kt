package com.github.jameshnsears.cameraoverlay.view.common

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun CommonPermissionsButton(icon: ImageVector, title: String) {
    val context = LocalContext.current

    ElevatedButton(
        onClick = { Toast.makeText(context, "Clicked on Icon Button", Toast.LENGTH_SHORT).show() },
        modifier = Modifier
            .padding(2.dp)
            .width(280.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 16.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = title
            )
        }
    }
}

@Preview
@Composable
fun PreviewCommonPermissionsButton() {
    CommonPermissionsButton(
        Icons.Outlined.HelpOutline, "..."
    )
}
