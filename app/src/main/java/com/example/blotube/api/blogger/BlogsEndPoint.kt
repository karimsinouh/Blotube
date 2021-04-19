package com.example.blotube.api.blogger

import com.example.blotube.api.ApiConstants
import com.example.blotube.data.ResponsePage
import com.example.blotube.data.blogger.Blog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BlogsEndPoint {

    @GET("blogs/${ApiConstants.BLOG_ID}/posts?key=${ApiConstants.API_KEY}&fetchImages=true")
    suspend fun getPosts():Response<ResponsePage<Blog>>

    @GET("blogs/${ApiConstants.BLOG_ID}/posts/{post_id}?key=${ApiConstants.API_KEY}&fetchImages=true")
    suspend fun getPost( @Path("post_id") postId:String ):Response<Blog>

    @GET("blogs/${ApiConstants.BLOG_ID}/posts?key=${ApiConstants.API_KEY}&fetchImages=true")
    suspend fun getMorePosts( @Query("pageToken") pageToken:String ):Response<ResponsePage<Blog>>

}