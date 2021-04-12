package com.example.blotube.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel :ViewModel(){

    val menuItems:List<Screen> = listOf(
        Screen.ScreenHome,
        Screen.ScreenVideos,
        Screen.ScreenSearch,
        Screen.ScreenPlaylists,
        Screen.ScreenBlogs
    )
}