package com.example.blotube.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.blogger.BlogsRepository
import com.example.blotube.api.youtube.YoutubeRepository
import com.example.blotube.data.blogger.Blog
import com.example.blotube.data.youtube.items.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val blogger: BlogsRepository,
    private val youtube:YoutubeRepository
) :ViewModel(){

    val menuItems:List<Screen> = listOf(
        Screen.ScreenHome,
        Screen.ScreenVideos,
        Screen.ScreenSearch,
        Screen.ScreenPlaylists,
        Screen.ScreenBlogs
    )

    init {
        loadPosts()
        loadVideos()
    }

    //pages tokens
    private var postsNextPageToken=""
    private var videosNextPageToken=""
    private var playlistsNextPageToken=""

    //loading states
    val videosLoading= mutableStateOf(true)
    val playlistsLoading= mutableStateOf(true)
    val postsLoading= mutableStateOf(true)
    val searchLoading= mutableStateOf(true)


    //states lists
    val blogs= mutableStateListOf<Blog>()
    val videos= mutableStateListOf<VideoItem>()



    val message= mutableStateOf<ScreenMessage?>(null)

    fun loadPosts()=viewModelScope.launch{
        blogger.getPosts {

            postsLoading.value=false

            if(it.isSuccessful){
                postsNextPageToken=it.data?.nextPageToken?:""
                blogs.addAll(it.data?.items?: emptyList())
            }else{
                message.value= ScreenMessage("blogs",it.message!!)
            }
        }
    }

    fun loadVideos()=viewModelScope.launch {
        youtube.getVideos(videosNextPageToken){
            videosLoading.value=false
            if(it.isSuccessful){
                videos.addAll(it.data?.items?: emptyList())
            }else{
                message.value=ScreenMessage("videos",it.message!!)
            }
        }
    }

}