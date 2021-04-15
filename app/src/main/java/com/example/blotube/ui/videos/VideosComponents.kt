package com.example.blotube.ui.videos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.data.blogger.Blog
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.ui.blogs.Author
import com.example.blotube.ui.theme.ImagePlaceholder
import com.example.blotube.ui.theme.RoundedShape
import com.google.accompanist.coil.CoilImage
import com.example.blotube.R



@Composable
fun VideoItem(video:VideoItem, onClick: ()->Unit){

    Box(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }){

        Column(Modifier.padding(12.dp),verticalArrangement = Arrangement.spacedBy(4.dp)) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),contentAlignment = Alignment.Center ){

                CoilImage(
                    data=video.snippet.thumbnails.medium.url,
                    contentDescription="",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedShape),
                    contentScale = ContentScale.Crop,
                    loading = { ImagePlaceholder() }
                )

                Image(painter = painterResource(id = R.drawable.play_video),contentDescription = "")


            }

            Text(
                text = video.snippet.title,
                fontSize = 20.sp,
                maxLines = 2
            )

            Text(video.snippet.publishedAt)

        }
    }

}


@Composable
fun VideoItemSmall(video: VideoItem, onClick: () -> Unit){
    Box(Modifier.clickable { onClick() }) {
        Row(Modifier.padding(8.dp)) {

            CoilImage(
                data=video.snippet.thumbnails.medium.url,
                contentDescription="",
                modifier = Modifier
                    .width(140.dp)
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

@Composable
fun PagerVideoItem(){

    val page=3

}