package com.example.blotube.ui.search

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blotube.R
import com.example.blotube.data.blogger.Blog
import com.example.blotube.data.youtube.items.SearchItem
import com.example.blotube.ui.blogs.BlogItemSmall
import com.example.blotube.ui.blogs.showPost
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.playlists.showPlaylist
import com.example.blotube.ui.playlists.showSearchPlaylist
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.ui.videos.showVideoInfo

const val SEARCH_YOUTUBE=3
const val SEARCH_BLOG=2

@Composable
fun Search(
    vm:MainViewModel
){

    val context= LocalContext.current
    val dialogState= remember { mutableStateOf(false) }
    val q=remember{ mutableStateOf("") }
    
    val searchType = remember{ mutableStateOf(SEARCH_BLOG) }

    val youtubeItems= remember{ mutableStateListOf<SearchItem>() }
    val blogs= remember { mutableListOf<Blog>() }


    Column {

        Column(Modifier.padding(8.dp)) {
            SearchBar(value = q.value, onValueChange = { q.value=it }) {

                when(searchType.value){

                    SEARCH_BLOG->vm.searchInBlog(q.value){
                        blogs.clear()
                        blogs.addAll(it)
                    }

                    SEARCH_YOUTUBE-> vm.searchInYoutube(q.value){
                        youtubeItems.clear()
                        youtubeItems.addAll(it)
                    }

                }
            }
            FilterButtons(onReverseList = {
                blogs.reverse()
            }) {
                dialogState.value=true
            }
        }

        if(vm.searchLoading.value)
            CenterProgressBar()
        else
            when(searchType.value){
                SEARCH_BLOG->{
                    LazyColumn {
                        items(blogs){item->
                            BlogSearchItem(item){
                                showPost(context,item.id!!)
                            }
                            Divider()
                        }
                    }
                }

                SEARCH_YOUTUBE->{
                    LazyColumn {
                        items(youtubeItems){item->
                            SearchItem(item) {
                                openSearchItem(context,item)
                            }
                        }
                    }
                }
            }


    }
    
    
    if (dialogState.value){
        
        AlertDialog(
            onDismissRequest = { dialogState.value=false },

            title={Text("Search In")},

            text={
                 SearchFilterDialogContent(
                     searchType = searchType.value
                 ){
                     searchType.value=it
                 }
            },

            buttons = {
                Box(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ){
                    TextButton(onClick = { dialogState.value=false}) {
                        Text(stringResource(R.string.done))
                    }
                }
            }
        )
        
    }



}

fun openSearchItem(context: Context, item: SearchItem) {
    if (item.id.kind=="youtube#video"){
        showVideoInfo(context,item.id.videoId!!)
    }else{
        showSearchPlaylist(context,item)
    }
}

