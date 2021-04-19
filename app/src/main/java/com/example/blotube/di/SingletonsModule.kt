package com.example.blotube.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.blotube.api.ApiConstants
import com.example.blotube.api.blogger.BlogsEndPoint
import com.example.blotube.api.database.Database
import com.example.blotube.api.youtube.YoutubeEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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


    @Singleton
    @Provides
    fun blogsApi(retrofit:Retrofit.Builder)=retrofit.baseUrl(ApiConstants.BLOGGER_BASE_URL).build().create(BlogsEndPoint::class.java)


    @Singleton
    @Provides
    fun youtubeApi(retrofit:Retrofit.Builder)=retrofit.baseUrl(ApiConstants.YOUTUBE_BASE_URL).build().create(YoutubeEndPoint::class.java)

    @Singleton
    @Provides
    fun database(@ApplicationContext c:Context)=
        Room.databaseBuilder(c,Database::class.java,"").fallbackToDestructiveMigration().build()

}