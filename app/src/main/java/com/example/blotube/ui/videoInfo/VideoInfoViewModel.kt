package com.example.blotube.ui.videoInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoInfoViewModel @Inject constructor():ViewModel() {

    private val _seconds=MutableLiveData(0f)

    val seconds:LiveData<Float> =_seconds

    fun setSeconds(sec:Float){
        _seconds.value=sec
    }
}