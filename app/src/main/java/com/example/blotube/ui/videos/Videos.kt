package com.example.blotube.ui.videos

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.ui.videoInfo.VideoInfoActivity

@ExperimentalFoundationApi
@Composable
fun Videos(vm:MainViewModel){

    val c= LocalContext.current

    if (vm.videosLoading.value) {
        CenterProgressBar()
    }else{

        LazyColumn {
            items(vm.videos){item->
                VideoItem(item){
                    showVideoInfo(c,item.snippet.resourceId?.videoId!!)
                }
                Divider()
            }
        }

    }

}


private fun showVideoInfo(c:Context,videoId:String){
    val intent=Intent(c,VideoInfoActivity::class.java)
    intent.putExtra("video_id",videoId)
    c.startActivity(intent)
}