package com.example.blotube.ui.videoInfo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.database.Database
import com.example.blotube.api.database.entity.VideosEntity
import com.example.blotube.api.youtube.YoutubeRepository
import com.example.blotube.data.youtube.items.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoInfoViewModel @Inject constructor(
    private val youtube:YoutubeRepository,
    private val database: Database
):ViewModel() {
    val video= mutableStateOf<VideoItem?>(null)
    val message= mutableStateOf<String?>(null)

    fun loadVideo(id:String)=viewModelScope.launch{
        youtube.getVideo(id){
            if (it.isSuccessful)
                video.value=it.data
            else
                message.value=it.message
        }
    }

    fun exists(id:String)=database.videos().exists(id)

    fun onWatchLaterChecked(checked:Boolean){
        if(checked)
            addToWatchLater(video.value!!)
        else
            removeFromWatchLater(video.value?.id!!)
    }

    private fun addToWatchLater(video:VideoItem)=viewModelScope.launch{
        val item=VideosEntity(video.snippet.title,video.snippet.thumbnails.medium.url,video.id!!)
        database.videos().insert(item)
    }

    private fun removeFromWatchLater(id:String)=viewModelScope.launch{
        database.videos().delete(id)
    }
}