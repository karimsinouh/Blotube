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

    object ScreenHome:Screen("home", R.string.home,Icons.Outlined.Home)
    object ScreenVideos:Screen("videos", R.string.videos,Icons.Outlined.PlayArrow)
    object ScreenPlaylists:Screen("playlists", R.string.playlists,Icons.Outlined.PlaylistPlay)
    object ScreenSearch:Screen("search", R.string.search,Icons.Outlined.Search)
    object ScreenBlogs:Screen("blogs", R.string.blogs,Icons.Outlined.Book)


}
