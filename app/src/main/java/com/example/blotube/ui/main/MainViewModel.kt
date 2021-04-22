package com.example.blotube.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.blogger.BlogsRepository
import com.example.blotube.api.youtube.YoutubeRepository
import com.example.blotube.data.blogger.Blog
import com.example.blotube.data.youtube.items.PlaylistItem
import com.example.blotube.data.youtube.items.SearchItem
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
        loadPlaylists()
    }

    //pages tokens
    var postsNextPageToken=""
    var videosNextPageToken=""
    var playlistsNextPageToken=""

    //loading states
    val videosLoading= mutableStateOf(true)
    val playlistsLoading= mutableStateOf(true)
    val postsLoading= mutableStateOf(true)
    val searchLoading= mutableStateOf(false)


    //states lists
    val blogs= mutableStateListOf<Blog>()
    val videos= mutableStateListOf<VideoItem>()
    val playlists= mutableStateListOf<PlaylistItem>()

    val message= mutableStateOf<ScreenMessage?>(null)

    fun loadPosts()=viewModelScope.launch{

        try{
            postsLoading.value=true
        }catch (e:NullPointerException){}

        blogger.getPosts(postsNextPageToken) {
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

        try{
            videosLoading.value=true
        }catch (e:NullPointerException){}

        youtube.getVideos(videosNextPageToken){
            videosLoading.value=false
            if(it.isSuccessful){
                videosNextPageToken=it.data?.nextPageToken?:""
                videos.addAll(it.data?.items?: emptyList())
            }else{
                message.value=ScreenMessage("videos",it.message!!)
            }
        }
    }

    fun loadPlaylists()=viewModelScope.launch {

        try{
            playlistsLoading.value=true
        }catch (e:NullPointerException){}

        youtube.getPlaylists(playlistsNextPageToken){
            playlistsLoading.value=false
            if(it.isSuccessful){

                playlists.addAll(it.data?.items ?: emptyList())
                playlistsNextPageToken=it.data?.nextPageToken?:""

            }else{
                message.value= ScreenMessage("playlists",it.message!!)
            }
        }
    }

    fun searchInBlog(q:String,listener:(List<Blog>)->Unit){
        searchLoading.value=true
        viewModelScope.launch {
            blogger.search(q){
                searchLoading.value=false
                if(it.isSuccessful){
                    listener(it.data ?: emptyList())
                }else{
                    message.value= ScreenMessage(Screen.ScreenSearch.root,it.message!!)
                }
            }
        }
    }

    fun searchInYoutube(q:String,listener:(List<SearchItem>)->Unit){
        searchLoading.value=true
        viewModelScope.launch {
            youtube.search(q){
                searchLoading.value=false

                if (it.isSuccessful)
                    listener(it.data ?: emptyList())
                else
                    message.value= ScreenMessage(Screen.ScreenSearch.root,it.message!!)

            }
        }
    }

}