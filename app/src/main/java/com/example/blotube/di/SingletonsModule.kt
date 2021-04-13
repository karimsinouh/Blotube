package com.example.blotube.di

import com.example.blotube.api.ApiConstants
import com.example.blotube.api.blogger.BlogsEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SingletonsModule {

    @Provides
    @Singleton
    fun retrofit()=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())


    @Provides
    fun blogsApi(retrofit:Retrofit.Builder)=retrofit.baseUrl(ApiConstants.BLOGGER_BASE_URL).build().create(BlogsEndPoint::class.java)

    //@Provides
    //fun youtubeApi(retrofit:Retrofit.Builder)=retrofit.baseUrl(ApiConstants.YOUTUBE_BASE_URL).build().create(YoutubeEndPoint::class.java)

}