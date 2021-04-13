package com.example.blotube.api.blogger

import com.example.blotube.api.ApiConstants
import com.example.blotube.data.ResponsePage
import com.example.blotube.data.blogger.Blog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogsEndPoint {

    @GET("blogs/${ApiConstants.BLOG_ID}/posts?key=${ApiConstants.API_KEY}&fetchImages=true")
    suspend fun getBlogs( ):Response<ResponsePage<List<Blog>>>

}