package com.example.blotube.api.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.blotube.api.database.entity.PostsEntity
import com.example.blotube.api.database.entity.VideosEntity

@Dao
interface PostsDAO {

    @Query("SELECT * FROM Posts")
    fun getAll():LiveData<List<PostsEntity>>

    @Insert(entity = PostsEntity::class)
    fun insert(item:PostsEntity)

    @Delete(entity = PostsEntity::class)
    fun delete(item:PostsEntity)

    @Query("DELETE FROM Posts")
    fun deleteAll()

    @Query("SELECT EXISTS (SELECT 1 FROM Posts WHERE id=:id)")
    fun exists(id:String):Boolean
}