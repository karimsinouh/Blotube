package com.example.blotube.api.youtube

import com.example.blotube.data.ResponsePage
import com.example.blotube.data.Result
import com.example.blotube.data.youtube.items.VideoItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YoutubeRepository @Inject constructor(
    private val api:YoutubeEndPoint
) {

    suspend fun getVideos(pageToken:String,listener:(Result<ResponsePage<VideoItem>>)->Unit){
        api.getVideos(pageToken).apply {
            listener(Result(isSuccessful,body(),message()))
        }
    }

}