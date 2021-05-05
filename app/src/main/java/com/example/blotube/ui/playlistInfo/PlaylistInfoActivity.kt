package com.example.blotube.ui.playlistInfo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
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
import com.example.blotube.data.youtube.Snippet
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.ui.description.DescriptionDialog
import com.example.blotube.ui.theme.BlotubeTheme
import com.example.blotube.ui.videoInfo.CustomYoutubePlayer
import com.example.blotube.ui.videos.shareVideo
import com.example.blotube.util.NightMode
import com.example.blotube.util.asDate
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistInfoActivity: ComponentActivity() {

    private lateinit var playlistId:String
    private lateinit var playlistThumbnail:String
    private lateinit var playlistTitle:String

    private val listener by lazy{
        object :AbstractYouTubePlayerListener(){

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                vm.video.observe(this@PlaylistInfoActivity){
                    youTubePlayer.loadVideo(it?.id!!,0f)
                }

            }

        }
    }

    private val vm by viewModels<PlaylistInfoViewModel>()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Blotube_Splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        playlistId=intent.getStringExtra("playlist_id")!!
        playlistTitle=intent.getStringExtra("playlist_title")!!
        playlistThumbnail=intent.getStringExtra("playlist_thumbnail")!!

        vm.loadPlaylistVideos(playlistId)

        setContent {

            val scope= rememberCoroutineScope()

            val nightMode=NightMode.isEnabled(this).collectAsState(initial = false)

            BlotubeTheme(nightMode.value) {

                    //bottom sheet
                    val bottomSheetState= rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))

                    val video=vm.video.observeAsState(null)

                    BottomSheetScaffold(
                        scaffoldState= bottomSheetState,
                        sheetContent = {
                            video.value?.let {
                                DescriptionDialog(it.snippet)
                            }
                        },
                        sheetPeekHeight = 0.dp,
                    ){
                        Column {

                            if(video.value!=null){
                                CustomYoutubePlayer(
                                    listener = listener
                                ) {
                                    lifecycle.addObserver(it)
                                }
                            }
                            Content(
                                video.value,
                                onTitleClick = {
                                    scope.launch {
                                        bottomSheetState.bottomSheetState.expand()
                                    }
                                }
                            )

                        }
                    }
                
            }

        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Content(video:VideoItem?,onTitleClick:(Snippet)->Unit)= LazyColumn{

        //the header of the page
        item {
            Column {

                if (video==null) {

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
                        text = video.snippet.title,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onTitleClick(video.snippet)
                            }
                    )

                    Text(
                        video.snippet.publishedAt.asDate(),
                        modifier = Modifier.padding(8.dp))

                    Spacer(modifier = Modifier.height(8.dp))

                    val existsInWatchHistory=vm.exists(video.id!!).observeAsState()

                    VideoButtons(
                        statistics = video.statistics!!,
                        exists = existsInWatchHistory.value?:false,
                        onShareClick = {
                            shareVideo(this@PlaylistInfoActivity,video.id)
                        },
                        onWatchLaterChecked= {
                            vm.onWatchLaterChecked(it)
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