package com.example.blotube.ui.playlistInfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
import com.example.blotube.R
import com.example.blotube.ui.theme.BlotubeTheme
import com.example.blotube.ui.videoInfo.CustomYoutubePlayer
import com.example.blotube.ui.videos.shareVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

@ExperimentalFoundationApi
@AndroidEntryPoint
class PlaylistInfoActivity: ComponentActivity() {

    private lateinit var playlistId:String
    private lateinit var playlistThumbnail:String
    private lateinit var playlistTitle:String

    private val tracker by lazy{
        YouTubePlayerTracker()
    }

    private val listener by lazy{
        object :AbstractYouTubePlayerListener(){

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(vm.video.value?.id!!,0f)
                youTubePlayer.addListener(tracker)
            }

        }
    }

    private val vm by viewModels<PlaylistInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playlistId=intent.getStringExtra("playlist_id")!!
        playlistTitle=intent.getStringExtra("playlist_title")!!
        playlistThumbnail=intent.getStringExtra("playlist_thumbnail")!!

        vm.loadPlaylistVideos(playlistId)

        setContent {

            BlotubeTheme {
                Column {
                    if(vm.video.value!=null){
                        CustomYoutubePlayer(
                            listener = listener
                        ) {
                            lifecycle.addObserver(it)
                        }
                    }
                    Content()
                }
            }

        }

    }

    @Composable
    private fun Content()= LazyColumn{

        //the header of the page
        item {
            Column {

                if (vm.video.value==null) {

                    CoilImage(
                        data=playlistThumbnail,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                        )

                    Text(playlistTitle,fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp))
                }
                else{

                    Text(
                        text = vm.video.value!!.snippet.title,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        vm.video.value!!.snippet.publishedAt,
                        modifier = Modifier.padding(8.dp))
                    Spacer(modifier = Modifier.height(8.dp))

                    VideoButtons(statistics = vm.video.value!!.statistics!!,
                        onShareClick = {
                                       shareVideo(this@PlaylistInfoActivity,vm.video.value?.id!!)
                        },
                        onWatchLaterClick={

                        })
                }
            }
        }


        if(vm.isLoading.value && vm.videos.isEmpty())
            item {
                CenterProgressBar()
            }
        else {
            stickyHeader {
                TextHeader(getString(R.string.videos))
            }
            itemsIndexed(vm.videos) {index, item ->
                VideoItemSmall(item) {
                    vm.loadVideo(item.snippet.resourceId?.videoId!!)
                }

                val canLoadMore=vm.videos.isNotEmpty() && vm.pageToken!="" || vm.videos.isEmpty() && vm.pageToken==""

                if((index+1)==vm.videos.size && !vm.isLoading.value && canLoadMore){
                    vm.loadPlaylistVideos(playlistId)
                }

            }

            val isLoadingMore=vm.isLoading.value && vm.videos.isNotEmpty()
            if(isLoadingMore){
                item{
                    CenterProgressBar(false)
                }
            }

        }



    }

    @Composable
    private fun TextHeader(text:String){
        Box(
            Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()){
            Text(text =text,modifier = Modifier.padding(10.dp))
        }
    }

}