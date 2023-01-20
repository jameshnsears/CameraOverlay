package com.github.jameshnsears.cameraoverlay.view.main.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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
                text = stringResource(R.string.about_dialog),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            AboutDialogRow(context)
        },
        confirmButton = {},
        dismissButton = {},
    )
}

@Composable
fun AboutDialogRow(context: Context) {
    Column {
        Row(Modifier.padding(bottom = 8.dp)) {
            ClickableText(
                text = AnnotatedString(stringResource(R.string.about_dialog_privacy_policy)),
                style = TextStyle(color = Color.Blue, fontSize = 16.sp),
                onClick = {
                    startActivity(
                        context,
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://jameshnsears.github.io/CameraOverlay/")
                        ),
                        null
                    )
                }
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.align(Alignment.CenterVertically)) {
                SelectionContainer {
                    Text(
                        BuildConfig.VERSION_NAME,
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                    )
                }
                SelectionContainer {
                    Text(BuildConfig.GIT_HASH)
                }
            }
            Column {
                IconButton(
                    onClick = { linkToGitHub(context) },
                    modifier = Modifier.width(80.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_github_logo),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

fun linkToGitHub(context: Context) {
    startActivity(
        context,
        Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jameshnsears/cameraoverlay")),
        null
    )
}

@Preview
@Composable
fun PreviewAboutDialog() {
    val context = LocalContext.current
    AboutDialogRow(context)
}
