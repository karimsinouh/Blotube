package com.example.blotube.api.youtube

import com.example.blotube.data.ResponsePage
import com.example.blotube.data.Result
import com.example.blotube.data.youtube.items.PlaylistItem
import com.example.blotube.data.youtube.items.SearchItem
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

    suspend fun getVideo(id:String,listener: (Result<VideoItem>) -> Unit){
        api.getVideo(id).apply {
            listener(Result(isSuccessful, body()?.items?.get(0),message()))
        }
    }

    suspend fun getPlaylists(pageToken:String,listener: (Result<ResponsePage<PlaylistItem>>) -> Unit){
        api.getPlaylists(pageToken).apply {
            listener(Result(isSuccessful,body(),message()))
        }
    }

    suspend fun getPlaylistVideos(id:String,pageToken:String,listener: (Result<ResponsePage<VideoItem>>) -> Unit){
        api.getPlaylistVideos(id,pageToken).apply {
            listener(Result(isSuccessful,body(),message()))
        }
    }

    suspend fun search(q:String,listener: (Result<List<SearchItem>>) -> Unit){
        api.search(q).apply {
            listener(Result(isSuccessful,body()?.items,message()))
        }
    }

}