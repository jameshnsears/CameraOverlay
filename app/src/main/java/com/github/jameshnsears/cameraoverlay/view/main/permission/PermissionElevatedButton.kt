package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
// Higher-Order function, as onClick is lambda
fun PermissionElevatedButton(
    onClick: () -> Unit,
    enabled: Boolean,
    icon: ImageVector,
    title: String
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(2.dp)
            .width(200.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled
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