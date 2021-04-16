package com.example.blotube.ui.videoInfo

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.util.Formatter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.example.blotube.R
import com.example.blotube.ui.videos.shareVideo

/**
 * this is a custom video player
 * we return the instance of the YouTubePlayerView because we need it from the activity
 * */
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


@Composable
fun VideoInfoLayout(video:VideoItem){

    val scrollState= rememberScrollState()
    val context= LocalContext.current

    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(scrollState),
        ) {

        Text(
            text = video.snippet.title,
            fontSize = 24.sp
        )

        Text(video.snippet.publishedAt)

        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {

            IconText(
                Formatter.statistics(video.statistics?.viewCount),
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                Icons.Outlined.PanoramaFishEye,
            )

            IconText(
                Formatter.statistics(video.statistics?.likeCount),
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                Icons.Outlined.ThumbUp,
            )

            IconText(
                Formatter.statistics(video.statistics?.dislikeCount),
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                Icons.Outlined.ThumbDown,
            )

            IconText(
                stringResource(id = R.string.share),
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                Icons.Outlined.Share,
                true
            ){
                shareVideo(context,video.id!!)
            }

            IconText(
                stringResource(id = R.string.later),
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                Icons.Outlined.WatchLater,
                true
            ){

            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = video.snippet.description)

    }
}


/**
*This composable shows an icon with text
 * param enabled: if true it will be clickable, if false it will just show the text
 * and an icon
 * */
@Composable
fun IconText(
    text:String,
    modifier: Modifier,
    icon:ImageVector,
    enabled:Boolean?=false,
    onClick:()->Unit?={}
)=if (enabled!!){
        IconButton(
            onClick = { onClick() },
            modifier = modifier
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
                Icon(imageVector = icon, contentDescription = "")
                Text(text = text)
            }
        }
}else{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(imageVector = icon, contentDescription = "")
        Text(text = text)
    }
}