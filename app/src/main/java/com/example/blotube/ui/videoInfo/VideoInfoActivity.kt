package com.example.blotube.ui.videoInfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.ui.videos.shareVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoInfoActivity:ComponentActivity() {

    private val listener by lazy{
        object :AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoId,vm.seconds.value?:0f)
                youTubePlayer.addListener(tracker)
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                super.onVideoDuration(youTubePlayer, duration)
                vm.setSeconds(duration)
            }
        }
    }


    private val tracker by lazy {
        YouTubePlayerTracker()
    }

    private lateinit var videoId:String

    private val vm by viewModels<VideoInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoId=intent.getStringExtra("video_id") ?: "none"

        if(vm.video.value==null)
            vm.loadVideo(videoId)

        setContent {

            Column {

                CustomYoutubePlayer(
                    listener = listener,
                    modifier = Modifier,
                ){
                    lifecycle.addObserver(it)
                }

                if (vm.video.value==null)
                    CenterProgressBar()
                else
                    VideoInfoLayout(
                        vm.video.value!!,
                        onShareClick = { shareVideo(this@VideoInfoActivity,videoId)},
                        onWatchLaterClick = { addToWatchLater() }
                        )

            }

        }



    }

    private fun addToWatchLater() {

    }


}