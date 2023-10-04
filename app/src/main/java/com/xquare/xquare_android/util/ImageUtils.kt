package com.xquare.xquare_android.util

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun rememberAsyncGifImagePainter(data: Any?): AsyncImagePainter {
    val context = LocalContext.current

    val model = ImageRequest.Builder(context)
        .data(data)
        .apply {
            size(Size.ORIGINAL)
        }.build()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    return rememberAsyncImagePainter(
        model = model,
        imageLoader = imageLoader,
    )
}
