package com.github.jameshnsears.cameraoverlay.view.photo.dialog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import timber.log.Timber

@Composable
fun PhotoDialogCollection(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_collections),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            PhotoDialogCollection()
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Composable
fun PhotoDialogCollection() {
    val radioMediaStore = stringResource(R.string.select_photo_dialog_collections_media_store)
    val radioStorageAccessFramework =
        stringResource(R.string.select_photo_dialog_collections_access_framework)

    var selected by remember { mutableStateOf(radioMediaStore) }

    Column {
        val onSelectedChange = { text: String ->
            selected = text
        }

        RowMediaStore(radioMediaStore, selected, onSelectedChange)

        Column {
            RowStorageAccessFramework(radioStorageAccessFramework, selected, onSelectedChange)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonStorageAccessFrameworkFile(radioStorageAccessFramework, selected)

                ButtonStorageAccessFrameworkFolder(radioStorageAccessFramework, selected)
            }
        }
    }
}

@Composable
private fun RowStorageAccessFramework(
    radioStorageAccessFramework: String,
    selected: String,
    onSelectedChange: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = (radioStorageAccessFramework == selected),
                onClick = { onSelectedChange(radioStorageAccessFramework) }
            )
    ) {
        RadioButton(
            selected = (radioStorageAccessFramework == selected),
            onClick = { onSelectedChange(radioStorageAccessFramework) },
//            colors = RadioButtonDefaults.colors(
//                selectedColor = MaterialTheme.colors.primary,
//                unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
//                disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
//            )
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(radioStorageAccessFramework, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
private fun RowMediaStore(
    radioMediaStore: String,
    selected: String,
    onSelectedChange: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = (radioMediaStore == selected),
                onClick = { onSelectedChange(radioMediaStore) }
            )
    ) {
        RadioButton(
            selected = (radioMediaStore == selected),
            onClick = { onSelectedChange(radioMediaStore) },
            colors = RadioButtonDefaults.colors(
//                selectedColor = MaterialTheme.colors.primary,
//                unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
//                disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            )
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(radioMediaStore, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun ButtonStorageAccessFrameworkFile(
    radioStorageAccessFramework: String,
    selected: String
) {
    val buttonState = remember { mutableStateOf(false) }
    val launcherAppInfoShowLocation = launcherAppInfoAccessLocation(buttonState)

    if (buttonState.value) {
        launcherAppInfoShowLocation
            .launch(
                getIntent(Intent.ACTION_OPEN_DOCUMENT)
            )
    }

    OutlinedButton(
        onClick = { buttonState.value = true },
        modifier = Modifier
            .width(120.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = (radioStorageAccessFramework == selected)
    ) {
        Icon(
            Icons.Outlined.Image,
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(R.string.select_photo_dialog_collections_file))
    }
}

@Composable
fun ButtonStorageAccessFrameworkFolder(
    radioStorageAccessFramework: String,
    selected: String
) {
    val buttonState = remember { mutableStateOf(false) }
    val launcherAppInfoShowLocation = launcherAppInfoAccessLocation(buttonState)

    if (buttonState.value) {
        launcherAppInfoShowLocation
            .launch(
                getIntent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            )
    }

    //     android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.intent.action.OPEN_DOCUMENT_TREE cat=[android.intent.category.OPENABLE] (has extras) }

    OutlinedButton(
        onClick = { buttonState.value = true },
        modifier = Modifier
            .width(120.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = (radioStorageAccessFramework == selected)
    ) {
        Icon(
            Icons.Outlined.Folder,
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(R.string.select_photo_dialog_collections_folder))
    }
}

private fun getIntent(intentAction: String): Intent {
    return Intent(intentAction).apply {
        if (intentAction == Intent.ACTION_OPEN_DOCUMENT) {
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        if (BuildConfig.DEBUG) {
            val authority = "com.android.externalstorage.documents"
            putExtra(
                DocumentsContract.EXTRA_INITIAL_URI,
                Uri.parse("content://$authority/document/primary:Pictures")
            )
        }

        if (intentAction == Intent.ACTION_OPEN_DOCUMENT) {
            // "image/tiff", "image/png", "image/bmp", "image/webp"
            type = "image/jpeg"
        }
    }
}

@Composable
fun launcherAppInfoAccessLocation(
    buttonState: MutableState<Boolean>,
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val launcherAppInfo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            Timber.d(it.data.toString())
        }

        // TODO Activity.RESULT_CANCELED; data = null
        val data = it.data

        buttonState.value = false
    }
    return launcherAppInfo
}

@Preview
@Composable
fun PreviewPhotoDialogCollection() {
    PhotoDialogCollection()
}
