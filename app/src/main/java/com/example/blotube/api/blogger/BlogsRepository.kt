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

    suspend fun getPosts(pageToken:String, listener:(Result<ResponsePage<Blog>>)->Unit){
        if (pageToken=="")
            api.getPosts().let {
                listener(Result(it.isSuccessful,it.body(),it.message()))
            }
        else
            api.getMorePosts(pageToken).let {
                listener(Result(it.isSuccessful,it.body(),it.message()))
            }
    }

    suspend fun getPost(blogId:String,listener:(Result<Blog>)->Unit){
        api.getPost(blogId).let {
            listener(Result(it.isSuccessful,it.body(),it.message()))
        }
    }

    suspend fun search(q:String,listener: (Result<List<Blog>>) -> Unit){
        api.search(q).apply {
            listener(Result(isSuccessful,body()?.items,message()))
        }
    }

}