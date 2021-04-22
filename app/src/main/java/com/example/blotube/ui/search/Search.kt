package com.example.blotube.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blotube.data.blogger.Blog
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar

@Composable
fun Search(
    vm:MainViewModel
){
    val q=remember{ mutableStateOf("") }
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
            LazyColumn {
                items(blogs){item->
                    SearchItem("post",post=item)
                }
            }

    }



}

