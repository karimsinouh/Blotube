package com.example.blotube.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.blotube.ui.blogs.BlogItem
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.main.Screen
import com.example.blotube.ui.playlists.PlaylistItem
import com.example.blotube.ui.videos.VideoItem

@ExperimentalFoundationApi
@Composable
fun Home(
    vm:MainViewModel,
    nav:NavController
){

    LazyColumn {

        //videos
        stickyHeader {
            Header(title = "Videos", actionText ="See All" ) {
                nav.navigate(Screen.ScreenVideos.root)
            }
        }
        //items(vm.videos){item->
          //  VideoItem(item) {}
        //}

        item {
            LazyRow {
                items(vm.videos){item->
                    VideoItem(item) {}
                }
            }
        }

        //playlists
        stickyHeader {
            Header(title = "Playlists", actionText ="See All" ) {
                nav.navigate(Screen.ScreenPlaylists.root)
            }
        }
        items(vm.playlists){item->
            PlaylistItem(item) {}
        }

        //blog posts

        stickyHeader {
            Header(title = "Blog Posts", actionText ="See All" ) {
                nav.navigate(Screen.ScreenBlogs.root)
            }
        }
        items(vm.blogs){item->
            BlogItem(item) {}
        }
    }
    
}

@Composable
fun Header(title:String,actionText:String,onAction:()->Unit){
    Box(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ){
        Row(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                title,
                fontSize = 18.sp,
                modifier= Modifier
                    .weight(0.9f)
                    .fillMaxWidth())

            TextButton(
                onClick = {
                    onAction()
                }
            ) {
                Text(text = actionText,color = MaterialTheme.colors.primary)
            }
        }
    }
}