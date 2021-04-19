package com.example.blotube.api.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.blotube.api.database.entity.VideosEntity

@Dao
interface VideosDAO {

    @Query("SELECT * FROM Videos")
    fun getAll():LiveData<List<VideosEntity>>

    @Insert(entity = VideosEntity::class)
    suspend fun insert(item:VideosEntity)

    @Query("DELETE FROM Videos WHERE id=:id")
    suspend fun delete(id:String)

    @Query("DELETE FROM Videos")
    suspend fun deleteAll()

    @Query("SELECT EXISTS (SELECT 1 FROM Videos WHERE id=:id)")
    suspend fun exists(id:String):Boolean

}