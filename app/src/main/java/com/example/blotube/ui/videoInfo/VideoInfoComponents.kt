package com.example.blotube.ui.videoInfo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun CustomYoutubePlayer(
    listener:AbstractYouTubePlayerListener,
    modifier: Modifier,
    returnView:(YouTubePlayerView)->Unit
){

    return AndroidView(
        modifier = modifier,
        factory = {
            YouTubePlayerView(it).apply {
                addYouTubePlayerListener(listener)
                returnView(this)
            }
        }
    )
}