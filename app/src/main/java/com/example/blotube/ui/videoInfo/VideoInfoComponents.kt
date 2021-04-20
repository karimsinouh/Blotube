package com.example.blotube.ui.videoInfo

import android.system.Os.remove
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.blotube.R
import com.example.blotube.data.youtube.Statistics
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.util.Formatter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * this is a custom video player
 * we return the instance of the YouTubePlayerView because we need it from the activity
 * */
@Composable
fun CustomYoutubePlayer(
    listener:AbstractYouTubePlayerListener,
    modifier: Modifier?=Modifier,
    returnView:(YouTubePlayerView)->Unit
){

    return AndroidView(
        modifier = modifier!!,
        factory = {
            YouTubePlayerView(it).apply {
                addYouTubePlayerListener(listener)
                returnView(this)
            }
        }
    )
}

/**
 * param exists: if the video exists in watch later
 * */
@Composable
fun VideoInfoLayout(
    video:VideoItem,
    exists: Boolean,
    onShareClick:()->Unit,
    onWatchLaterChecked: (Boolean) -> Unit
){

    val scrollState= rememberScrollState()

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

        VideoButtons(statistics = video.statistics!!,exists,onShareClick,onWatchLaterChecked)

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = video.snippet.description)

    }
}

/**
 * param exists: if the video exists in watch later
 * */
@Composable
fun VideoButtons(
    statistics: Statistics,
    exists:Boolean,
    onShareClick: () -> Unit,
    onWatchLaterChecked: (Boolean) -> Unit
    ){

    Row(Modifier.fillMaxWidth()) {

        IconText(
            Formatter.statistics(statistics.viewCount),
            Modifier
                .fillMaxWidth()
                .weight(1f),
            Icons.Outlined.PanoramaFishEye,
        )

        IconText(
            Formatter.statistics(statistics.likeCount),
            Modifier
                .fillMaxWidth()
                .weight(1f),
            Icons.Outlined.ThumbUp,
        )

        IconText(
            Formatter.statistics(statistics.dislikeCount),
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
            onShareClick()
        }



        IconToggleButton(
            checked = exists,
            onCheckedChange = { onWatchLaterChecked(it) },
            modifier = Modifier.weight(1f)
        ) {
            if (!exists){
                IconText(
                    stringResource(id = R.string.later),
                    Modifier.fillMaxWidth(),
                    Icons.Outlined.WatchLater,
                    false
                )
            }else{
                IconText(
                    stringResource(id = R.string.remove),
                    Modifier.fillMaxWidth(),
                    Icons.Outlined.Delete,
                    false
                )
            }
        }

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