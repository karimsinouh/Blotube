package com.example.blotube.ui.videoInfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
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
import android.view.WindowManager

import android.os.Build
import android.util.Log

@AndroidEntryPoint
class VideoInfoActivity:ComponentActivity() {

    private val listener by lazy{
        object :AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoId,0f)
            }
        }
    }

    private lateinit var videoId:String

    private val vm by viewModels<VideoInfoViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        videoId=intent.getStringExtra("video_id") ?: "none"

        if(vm.video.value==null)
            vm.loadVideo(videoId)

        setContent {

            val nightMode=NightMode.isEnabled(this).collectAsState(initial = false)

            BlotubeTheme(nightMode.value) {


                Surface(
                    color=MaterialTheme.colors.background,
                    contentColor= MaterialTheme.colors.onBackground,
                ) {

                    Column {

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

}