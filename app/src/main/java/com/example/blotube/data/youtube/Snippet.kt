package com.example.blotube.data.youtube

import com.example.blotube.data.youtube.thumbnails.Thumbnails
import java.io.Serializable

data class Snippet(
    val publishedAt:String,
    val title:String,
    val description:String,
    val thumbnails: Thumbnails,
    val playlistId:String?=null,
    val position:Int?=null,
    val resourceId: ResourceId?=null
):Serializable