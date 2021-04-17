package com.example.blotube.api.youtube

import com.example.blotube.api.ApiConstants.API_KEY
import com.example.blotube.api.ApiConstants.CHANNEL_ID
import com.example.blotube.api.ApiConstants.PLAYLIST_ID
import com.example.blotube.data.ResponsePage
import com.example.blotube.data.youtube.items.PlaylistItem
import com.example.blotube.data.youtube.items.SearchItem
import com.example.blotube.data.youtube.items.VideoItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeEndPoint {

    @GET("playlistItems?part=snippet&playlistId=$PLAYLIST_ID&maxResults=5&key=$API_KEY")
    suspend fun getVideos( @Query("pageToken") pageToken:String?="" ): Response<ResponsePage<VideoItem>>

    @GET("playlists?part=snippet,contentDetails&channelId=$CHANNEL_ID&maxResults=20&key=$API_KEY")
    suspend fun getPlaylists( @Query("pageToken") pageToken:String?="" ):Response<ResponsePage<PlaylistItem>>

    @GET("search?part=snippet&channelId=$CHANNEL_ID&key=$API_KEY&maxResults=5")
    suspend fun search( @Query("q") q:String,@Query("pageToken") pageToken:String?="" ):Response<ResponsePage<SearchItem>>

    @GET("videos?part=snippet&key=$API_KEY")
    suspend fun getSelectedVideos( @Query("id") ids:String ):Response<ResponsePage<VideoItem>>

    @GET("videos?part=snippet,contentDetails,statistics&key=$API_KEY")
    suspend fun getVideo( @Query("id") id:String ):Response<ResponsePage<VideoItem>>

    @GET("playlistItems?part=snippet,contentDetails&key=$API_KEY&maxResults=15")
    suspend fun getPlaylistVideos( @Query("playlistId") id:String, @Query("pageToken") token:String?="" ):Response<ResponsePage<VideoItem>>

}