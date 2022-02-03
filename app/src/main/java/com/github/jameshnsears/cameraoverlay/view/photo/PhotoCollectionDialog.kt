package com.github.jameshnsears.cameraoverlay.view.photo

import ButtonOk
import android.app.Activity
import android.content.Intent
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.jameshnsears.cameraoverlay.R
import timber.log.Timber

@Composable
fun PhotoCollectionDialog(openDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.select_photo_collections),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        text = {
            CollectionDialogRow()
        },
        confirmButton = {},
        dismissButton = {
            ButtonOk { openDialog.value = false }
        }
    )
}

@Composable
fun CollectionDialogRow() {
    val radioMediaStore = stringResource(R.string.select_photo_dialog_collections_media_store)
    val radioStorageAccessFramework =
        stringResource(R.string.select_photo_dialog_collections_access_framework)

    var selected by remember { mutableStateOf(radioMediaStore) }

    Column {
        val onSelectedChange = { text: String ->
            selected = text
        }

        Row(
            Modifier
                .padding(top = 20.dp)
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
                    selectedColor = MaterialTheme.colors.primary,
                    unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                )
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(radioMediaStore)
        }

        Column {
            Row(
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .selectable(
                        selected = (radioStorageAccessFramework == selected),
                        onClick = { onSelectedChange(radioStorageAccessFramework) }
                    )
            ) {
                RadioButton(
                    selected = (radioStorageAccessFramework == selected),
                    onClick = { onSelectedChange(radioStorageAccessFramework) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        disabledColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(radioStorageAccessFramework)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* ... */ },
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


                Button(
                    onClick = { /* ... */ },
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
        }
    }
}

@Composable
fun ButtonPicker() {
    val buttonState = remember { mutableStateOf(false) }

    val launcherAppInfoShowLocation = launcherAppInfoAccessLocation(buttonState)

    if (buttonState.value) {
        launcherAppInfoShowLocation

            .launch(
                // https://developer.android.com/training/data-storage/shared/documents-files
                // == shows how to access items metadata

                // TODO - a folder ACTION_OPEN_DOCUMENT_TREE
                // TODO - v. a single file: ACTION_OPEN_DOCUMENT
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        val authority = "com.android.externalstorage.documents"
//                        putExtra(
//                            DocumentsContract.EXTRA_INITIAL_URI,
//                            Uri.parse("content://${authority}/document/primary:Pictures"))
//                    }

                    // https://www.iana.org/assignments/media-types/media-types.xhtml#image
                    // only jpeg + tiff types can contain EXIF


                    // "image/tiff", "image/png", "image/bmp", "image/webp"
                    type = "image/jpeg"

                }
            )
    }


    Button(
        onClick = {
            buttonState.value = true
        },
        modifier = Modifier
            .width(120.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            imageVector = Icons.Outlined.Folder,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = stringResource(R.string.select_photo_dialog_collections_folder)
        )
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

        // Activity.RESULT_CANCELED; data = null

        val data = it.data

        buttonState.value = false
    }
    return launcherAppInfo
}

@Preview()
@Composable
fun PreviewPhotoCollectionDialog() {
    CollectionDialogRow()
}
