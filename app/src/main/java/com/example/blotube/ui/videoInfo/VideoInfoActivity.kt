package com.example.blotube.ui.videoInfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.ui.theme.BlotubeTheme
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.ui.videos.shareVideo
import com.example.blotube.util.NightMode
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

    @Inject
    lateinit var nightMode: NightMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        videoId=intent.getStringExtra("video_id") ?: "none"

        if(vm.video.value==null)
            vm.loadVideo(videoId)

        setContent {

            val nightMode=nightMode.isEnabled.collectAsState(initial = false)

            BlotubeTheme(nightMode.value) {
                Column(Modifier.background(MaterialTheme.colors.background)) {

                    CustomYoutubePlayer(
                        listener = listener,
                        modifier = Modifier,
                    ){
                        lifecycle.addObserver(it)
                    }

                    val exists=vm.exists(videoId).observeAsState()

                    if (vm.video.value==null)
                        CenterProgressBar()
                    else
                        VideoInfoLayout(
                            vm.video.value!!,
                            exists.value ?: false,
                            onShareClick = { shareVideo(this@VideoInfoActivity,videoId)},
                            onWatchLaterChecked = { vm.onWatchLaterChecked(it) },
                        )

                }
            }

        }



    }

}