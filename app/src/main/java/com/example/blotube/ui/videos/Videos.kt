package com.example.blotube.ui.videos

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar

@Composable
fun Videos(vm:MainViewModel){

    if (vm.videosLoading.value) {
        CenterProgressBar()
    }else{

        LazyColumn {
            items(vm.videos){item->

                VideoItemSmall(item){

                }

            }
        }

    }

}