package com.example.blotube.ui.theme

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun CenterProgressBar(){
    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){

        CircularProgressIndicator(strokeWidth = 3.dp)

    }
}

@Composable
fun Picture(
    url:String,
    modifier: Modifier?=Modifier,
    contentScale: ContentScale,
    onLoading: @Composable ()->Unit
){

    val imageBitmapState= remember{ mutableStateOf<ImageBitmap?>(null) }

    Box(modifier!!) {
        if (imageBitmapState.value!=null)
            Image(
                bitmap = imageBitmapState.value!!,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = contentScale,
                )
        else
            onLoading()
    }

    val customTarget=object :CustomTarget<Bitmap>(){
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            imageBitmapState.value=resource.asImageBitmap()
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }

    }

    Glide.with(LocalContext.current).asBitmap().load(url).into(customTarget)
}