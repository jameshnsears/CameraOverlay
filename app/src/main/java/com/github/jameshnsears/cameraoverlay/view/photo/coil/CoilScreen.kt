package com.github.jameshnsears.cameraoverlay.view.photo.coil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun CoilScreen(model: Any?) {
    Column {
        AsyncImage(
            model = model,
            contentDescription = null
        )
    }

    /*
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://example.com/image.jpg")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
        )



        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(screen.image.uri)
                .parameters(screen.image.parameters)
                .placeholderMemoryCacheKey(screen.placeholder)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.uri)
                .parameters(image.parameters)
                .build(),
            placeholder = ColorPainter(Color(image.color)),
            error = ColorPainter(Color.Red),
            onSuccess = { placeholder = it.result.memoryCacheKey },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .clickable { screenFlow.value = Screen.Detail(image, placeholder) }
        )
    }
     */
}

// https://developer.android.com/jetpack/compose/lists
@Preview
@Composable
fun LazyColumnDemo() {
    val itemsIndexedList = listOf("A", "B", "C")

    LazyColumn {
        itemsIndexed(itemsIndexedList) { index, item ->
            Text("Item at index $index is $item")
        }
    }
}