package com.example.blotube.ui.videoInfo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.youtube.YoutubeRepository
import com.example.blotube.data.youtube.items.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoInfoViewModel @Inject constructor(
    private val youtube:YoutubeRepository
):ViewModel() {

    private val _seconds=MutableLiveData(0f)
    val video= mutableStateOf<VideoItem?>(null)
    val message= mutableStateOf<String?>(null)

    val seconds:LiveData<Float> =_seconds

    fun setSeconds(sec:Float){
        _seconds.value=sec
    }

    fun loadVideo(id:String)=viewModelScope.launch{
        youtube.getVideo(id){
            if (it.isSuccessful)
                video.value=it.data
            else
                message.value=it.message
        }
    }
}