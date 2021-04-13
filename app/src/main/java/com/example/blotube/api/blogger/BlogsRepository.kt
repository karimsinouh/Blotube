package com.example.blotube.api.blogger

import com.example.blotube.api.ApiConstants
import com.example.blotube.data.ResponsePage
import com.example.blotube.data.Result
import com.example.blotube.data.blogger.Blog
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class BlogsRepository @Inject constructor (
    private val api:BlogsEndPoint
) {

    suspend fun getBlogs(pageToken:String?="",listener:(Result<ResponsePage<List<Blog>>>)->Unit){
        api.getBlogs().let {
            listener(Result(it.isSuccessful,it.body(),it.message()))
        }
    }

}