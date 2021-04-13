package com.example.blotube.api.blogger

import com.example.blotube.api.ApiConstants
import com.example.blotube.data.ResponsePage
import com.example.blotube.data.Result
import com.example.blotube.data.blogger.Blog
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlogsRepository {

    private val retrofit=Retrofit.Builder().baseUrl(ApiConstants.BLOGGER_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    private val api=retrofit.create(BlogsEndPoint::class.java)

    suspend fun getBlogs(pageToken:String?="",listener:(Result<ResponsePage<List<Blog>>>)->Unit){
        api.getBlogs().let {
            listener(Result(it.isSuccessful,it.body(),it.message()))
        }
    }

}