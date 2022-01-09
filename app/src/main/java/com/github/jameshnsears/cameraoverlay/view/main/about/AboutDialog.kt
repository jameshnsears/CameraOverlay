package com.github.jameshnsears.cameraoverlay.view.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R

@Composable
fun AboutDialog(openDialog: MutableState<Boolean>) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.about),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            AboutDialogRow(context)
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = {
                    openDialog.value = false
                },
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(stringResource(R.string.ok))
            }
        }
    )
}

@Composable
fun AboutDialogRow(context: Context) {
    var version = BuildConfig.VERSION_NAME
        .replace("-fdroid", "")
        .replace("-googleplay", "")
    version += "/" + BuildConfig.GIT_HASH

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.align(Alignment.CenterVertically)) {
            SelectionContainer {
                Text(version)
            }
        }
        Column {
            IconButton(
                onClick = { linkToGitHub(context) },
                 modifier = Modifier.width(70.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github_logo),
                    contentDescription = null,
                )
            }
        }
    }
}

fun linkToGitHub(context: Context) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse("https://github.com/jameshnsears/cameraoverlay")
    startActivity(context, openURL, null)
}

@Preview()
@Composable
fun Preview() {
    val context = LocalContext.current
    AboutDialogRow(context)
}
