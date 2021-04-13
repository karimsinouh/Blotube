package com.example.blotube.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.blogger.BlogsRepository
import com.example.blotube.data.blogger.Blog
import kotlinx.coroutines.launch

class MainViewModel :ViewModel(){

    val menuItems:List<Screen> = listOf(
        Screen.ScreenHome,
        Screen.ScreenVideos,
        Screen.ScreenSearch,
        Screen.ScreenPlaylists,
        Screen.ScreenBlogs
    )

    val blogsRepo=BlogsRepository()

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


    //states lists
    val blogs= mutableStateListOf<Blog>()

    val message= mutableStateOf<ScreenMessage?>(null)

    fun loadBlogs()=viewModelScope.launch{
        blogsRepo.getBlogs {

            blogsLoading.value=false

            if(it.isSuccessful){
                blogsNextPageToken=it.data?.nextPageToken?:""
                blogs.addAll(it.data?.items?: emptyList())
            }else{
                message.value= ScreenMessage("blogs",it.message!!)
            }
        }
    }


}