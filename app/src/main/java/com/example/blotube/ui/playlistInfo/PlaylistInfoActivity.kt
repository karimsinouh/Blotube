package com.example.blotube.ui.playlistInfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.ui.videoInfo.VideoButtons
import com.example.blotube.ui.videos.VideoItemSmall
import com.google.accompanist.coil.CoilImage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class PlaylistInfoActivity: ComponentActivity() {

    private lateinit var playlistId:String
    private lateinit var playlistThumbnail:String
    private lateinit var playlistTitle:String

    private val vm by viewModels<PlaylistInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playlistId=intent.getStringExtra("playlist_id")!!
        playlistTitle=intent.getStringExtra("playlist_title")!!
        playlistThumbnail=intent.getStringExtra("playlist_thumbnail")!!

        vm.loadPlaylistVideos(playlistId)

        setContent {

            Content()

        }

    }

    @Composable
    @Preview
    private fun Content()= LazyColumn{

        //the header of the page
        item {
            Column {
                if (vm.video.value==null) {

                    CoilImage(
                        data = playlistThumbnail,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {}

                    Text(playlistTitle,fontSize = 24.sp,modifier = Modifier.padding(8.dp))
                    Divider()
                }
                else{
                    CoilImage(
                        data = playlistThumbnail,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {}

                    Text(
                        text = vm.video.value!!.snippet.title,
                        fontSize = 24.sp
                    )

                    Text(vm.video.value!!.snippet.publishedAt)
                    Spacer(modifier = Modifier.height(8.dp))

                    VideoButtons(statistics = vm.video.value!!.statistics!!,
                        {  }, {  })
                }
            }
        }

        if(vm.isLoading.value)
            item {
                CenterProgressBar()
            }
        else
            items(vm.videos){item->
                VideoItemSmall(item!!) {
                    vm.loadVideo(item.snippet.resourceId?.videoId!!)
                }
            }

    }

}