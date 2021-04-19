package com.example.blotube.api.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Posts")
data class PostsEntity(
    val title:String,
    val thumbnail:String,
    @PrimaryKey val id:String,
)
