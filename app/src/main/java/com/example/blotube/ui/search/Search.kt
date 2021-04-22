package com.example.blotube.ui.search

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blotube.data.blogger.Blog
import com.example.blotube.data.youtube.items.SearchItem
import com.example.blotube.ui.blogs.BlogItemSmall
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar

private const val SEARCH_YOUTUBE=3
private const val SEARCH_BLOG=2

@Composable
fun Search(
    vm:MainViewModel
){
    val q=remember{ mutableStateOf("") }
    var searchType = SEARCH_BLOG

    val youtubeItems= remember{ mutableStateListOf<SearchItem>() }
    val blogs= remember { mutableListOf<Blog>() }


    Column {

        Column(Modifier.padding(8.dp)) {
            SearchBar(value = q.value, onValueChange = { q.value=it }) {
                vm.searchInBlog(q.value){
                    blogs.clear()
                    blogs.addAll(it)
                }
            }
            FilterButtons(onReverseList = {
                blogs.reverse()
            }) {}
        }

        if(vm.searchLoading.value)
            CenterProgressBar()
        else
            when(searchType){
                SEARCH_BLOG->{
                    LazyColumn {
                        items(blogs){item->
                            BlogSearchItem(item){

                            }
                            Divider()
                        }
                    }
                }

                SEARCH_YOUTUBE->{
                    LazyColumn {
                        items(youtubeItems){item->

                        }
                    }
                }
            }


    }



}

