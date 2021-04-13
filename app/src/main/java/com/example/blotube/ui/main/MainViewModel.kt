package com.example.blotube.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.blogger.BlogsRepository
import com.example.blotube.data.blogger.Blog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val blogsRepo: BlogsRepository
) :ViewModel(){

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


    //states lists
    val blogs= mutableStateListOf<Blog>()

    val message= mutableStateOf<ScreenMessage?>(null)

    fun loadBlogs()=viewModelScope.launch{
        blogsRepo.getPosts {

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