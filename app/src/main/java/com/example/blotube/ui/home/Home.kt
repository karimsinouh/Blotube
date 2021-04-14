package com.example.blotube.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blotube.ui.blogs.BlogItem
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.playlists.PlaylistItem
import com.example.blotube.ui.videos.VideoItem

@ExperimentalFoundationApi
@Composable
fun Home(vm:MainViewModel){

    LazyColumn {

        //videos

        stickyHeader {
            Text(text = "Videos",
                modifier = Modifier.background(MaterialTheme.colors.background).padding(8.dp).fillMaxWidth())
        }
        items(vm.videos){item->
            VideoItem(item) {}
        }

        //playlists

        stickyHeader {
            Text(text = "Playlists",modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp).fillMaxWidth()) }
        items(vm.playlists){item->
            PlaylistItem(item) {}
        }

        //blog posts

        stickyHeader { Text(text = "Blog Posts",modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp).fillMaxWidth()) }
        items(vm.blogs){item->
            BlogItem(item) {}
        }
    }
    
}
