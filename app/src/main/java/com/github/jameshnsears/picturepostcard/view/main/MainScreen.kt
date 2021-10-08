package com.github.jameshnsears.picturepostcard.view.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.picturepostcard.BuildConfig
import com.github.jameshnsears.picturepostcard.R
import com.github.jameshnsears.picturepostcard.stateholder.HelloStateHolder
import com.github.jameshnsears.picturepostcard.view.Navigation
import com.github.jameshnsears.picturepostcard.view.common.CommonPermissionsButton
import com.github.jameshnsears.picturepostcard.view.theme.PicturePostcardTheme
import com.github.jameshnsears.picturepostcard.viewmodel.HelloViewModel


@ExperimentalMaterial3Api
@Composable
fun MainScreen(navController: NavController) {
    PicturePostcardTheme {
        Scaffold(
            topBar = { CentreAlignedTopAppBar() },
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                ReasonForBeing()

                UsageCard()

                HoistedHello()

                PermissionsText()

                CommonPermissionsButton(
                    Icons.Outlined.Folder,
                    stringResource(R.string.permissions_media)
                )
                CommonPermissionsButton(
                    Icons.Outlined.LocationOn,
                    stringResource(R.string.permissions_location)
                )
                CommonPermissionsButton(
                    Icons.Outlined.Layers,
                    stringResource(R.string.permissions_display)
                )

                SelectPhoto(navController)

                Footer()
            }
        }
    }
}

@Composable
fun HoistedHello(helloViewModel: HelloViewModel = viewModel()) {
    val helloState by helloViewModel.helloStateHolder

    Hello(
        stateHolder = helloState,
        onNameChange = {
            helloViewModel.onNameChange(HelloStateHolder(it))
        }
    )
}

@Composable
fun Hello(stateHolder: HelloStateHolder, onNameChange: (String) -> Unit) {
    Column {
        if (stateHolder.name.isNotEmpty()) {
            Text(
                text = "Hello, " + stateHolder.name
            )
        }
        OutlinedTextField(
            value = stateHolder.name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

@Composable
fun CentreAlignedTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
    )
}

@Composable
fun ReasonForBeing() {
    Text(
        stringResource(R.string.permissions_justification),
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun UsageCard() {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.permissions_usage_0),
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            stringResource(R.string.permissions_usage_1),
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            stringResource(R.string.permissions_usage_2),
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            stringResource(R.string.permissions_usage_3),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun PermissionsText() {
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = "policy", annotation = "https://google.com/policy")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(stringResource(R.string.permissions_privacy_policy_1))
        }
        pop()
    }

    Column(
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Text(
            stringResource(R.string.permissions_required),
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.permissions_privacy_policy_0))
                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "policy",
                            start = offset,
                            end = offset
                        )
                            .firstOrNull()
                    }
                )
                Text(stringResource(R.string.permissions_privacy_policy_2))
            }
        }
    }
}

@Composable
fun SelectPhoto(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        ElevatedButton(
            onClick = { navController.navigate(Navigation.PHOTO_SCREEN) },
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = false
        ) {
            Icon(
                imageVector = Icons.Outlined.ImageSearch,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp),
            )
            Text(
                text = stringResource(R.string.select_photo)
            )
        }
    }
}

@Composable
fun Footer() {
    Row(
        Modifier
            .fillMaxSize()
            .padding(bottom = 4.dp)
    ) {
        Column(Modifier.align(Alignment.Bottom)) {
            Text(
                text = BuildConfig.VERSION_NAME + "/" + BuildConfig.GIT_HASH,
                fontSize = 14.sp
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.Bottom)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_github_logo),
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .align(Alignment.End)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(name = "Light Theme")
@Composable
fun PreviewPortrait() {
    MainScreen(rememberNavController())
}

@ExperimentalMaterial3Api
@Preview(name = "Landscape", widthDp = 720, heightDp = 720, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewLandscape() {
    MainScreen(rememberNavController())
}
