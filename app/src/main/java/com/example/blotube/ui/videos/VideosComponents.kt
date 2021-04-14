package com.example.blotube.ui.videos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.data.blogger.Blog
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.ui.theme.ImagePlaceholder
import com.example.blotube.ui.theme.RoundedShape
import com.google.accompanist.coil.CoilImage

@Composable
fun VideoItemSmall(video: VideoItem, onClick: () -> Unit){
    Box(Modifier.clickable { onClick() }) {
        Row(Modifier.padding(8.dp)) {

            CoilImage(
                data=video.snippet.thumbnails.medium.url,
                contentDescription="",
                modifier = Modifier
                    .width(120.dp)
                    .height(80.dp)
                    .clip(RoundedShape),
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder() }
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = video.snippet.title,
                    fontSize = 18.sp,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                Text(video.snippet.publishedAt)
            }

        }
    }
}