package com.example.blotube.ui.blogs

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar

@Composable
fun Blogs(
    vm:MainViewModel,
    nav:NavController
){

    if (vm.postsLoading.value){
        CenterProgressBar()
    }else{
        LazyColumn {
            items(vm.blogs){item->
                BlogItem(item){
                    nav.navigate("showBlogPost/"+item.id)
                }

                Divider()
            }
        }
    }

}