package com.example.blotube.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel :ViewModel(){

    val menuItems:List<Screen> = listOf(
        Screen.ScreenHome,
        Screen.ScreenVideos,
        Screen.ScreenSearch,
        Screen.ScreenPlaylists,
        Screen.ScreenBlogs
    )

    init {
        loadBlogs()
    }

    //pages tokens
    private var blogsNextPageToken=""
    private var videosNextPageToken=""
    private var playlistsNextPageToken=""

    //loading states
    val videosLoading= mutableStateOf(true)
    val playlistsLoading= mutableStateOf(true)
    val blogsLoading= mutableStateOf(true)
    val searchLoading= mutableStateOf(true)

    val message= mutableListOf<ScreenMessage>()

    fun loadBlogs(){

    }


}