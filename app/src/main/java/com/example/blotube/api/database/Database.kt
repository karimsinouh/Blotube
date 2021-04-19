package com.example.blotube.api.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blotube.api.database.dao.PostsDAO
import com.example.blotube.api.database.dao.VideosDAO
import com.example.blotube.api.database.entity.PostsEntity
import com.example.blotube.api.database.entity.VideosEntity

@Database(entities = [VideosEntity::class,PostsEntity::class],version = 1)
abstract class Database:RoomDatabase() {

    abstract fun videos():VideosDAO

    abstract fun posts():PostsDAO

}