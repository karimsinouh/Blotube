package com.example.blotube.ui.later

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blotube.R
import com.example.blotube.api.database.Database
import com.example.blotube.api.database.entity.VideosEntity
import com.example.blotube.ui.theme.ImagePlaceholder
import com.example.blotube.ui.theme.RoundedShape
import com.example.blotube.ui.videos.showVideoInfo
import com.example.blotube.util.Empty
import com.google.accompanist.coil.CoilImage

@Composable
fun WatchLater(db:Database){


    val videos=db.videos().getAll().observeAsState()

    val context= LocalContext.current

    videos.value?.let {
        if (it.isEmpty()){
            Empty(stringResource(R.string.empty_watch_later_text))
        }else
            LazyColumn {
                items(it.reversed()){item->
                    WatchLaterItem(item) {
                        showVideoInfo(context,item.id)
                    }
                }
            }
    }

}

@Composable
fun WatchLaterItem(video: VideosEntity, onClick: () -> Unit){

    Box(Modifier.clickable { onClick() }) {
        Row(Modifier.padding(8.dp)) {

            CoilImage(
                data=video.thumbnail,
                contentDescription=null,
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
                    text = video.title,
                    fontSize = 18.sp,
                    maxLines = 2
                )
            }

        }
    }

}