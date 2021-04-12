package com.example.blotube.ui.blogs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar

@Composable
fun Blogs(
    vm:MainViewModel,
    nav:NavController
){

    if (vm.blogsLoading.value){
        CenterProgressBar()
    }


}