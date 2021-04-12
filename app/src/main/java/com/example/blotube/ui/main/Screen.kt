package com.example.blotube.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.blotube.R

sealed class Screen(
    val root:String,
    @StringRes val label:Int,
    val icon:ImageVector,
){

    object ScreenHome:Screen("home", R.string.videos,Icons.Outlined.Home)
    object ScreenVideos:Screen("videos", R.string.playlists,Icons.Outlined.PlayCircle)
    object ScreenPlaylists:Screen("playlists", R.string.playlists,Icons.Outlined.PlaylistPlay)
    object ScreenBlogs:Screen("blogs", R.string.blogs,Icons.Outlined.Pages)
    object ScreenSearch:Screen("search", R.string.search,Icons.Outlined.Search)

}
