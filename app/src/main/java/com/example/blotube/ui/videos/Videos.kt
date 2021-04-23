package com.example.blotube.ui.videos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar

@ExperimentalFoundationApi
@Composable
fun Videos(vm:MainViewModel) {

    val c = LocalContext.current

    if (vm.videosLoading.value && vm.videos.isEmpty()) {
        CenterProgressBar()
    } else

        LazyColumn {

            itemsIndexed(vm.videos) { index, item ->
                VideoItem(item) {
                    showVideoInfo(c, item.snippet.resourceId?.videoId!!)
                }
                Divider()

                val canLoadMore=vm.videos.isNotEmpty() && vm.videosNextPageToken!="" || vm.videos.isEmpty() && vm.videosNextPageToken==""
                if ((index + 1) == vm.videos.size && !vm.videosLoading.value && canLoadMore) {
                    vm.loadVideos()
                }

            }

            val isLoadingMore = (vm.videos.isNotEmpty() && vm.videosLoading.value)
            if (isLoadingMore) {
                item {
                    CenterProgressBar(false)
                }
            }
        }


}
