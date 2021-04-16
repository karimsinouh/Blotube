package com.example.blotube.data.youtube.thumbnails

import java.io.Serializable

data class Thumbnail(
    val url:String,
    val width:Int,
    val height:Int
):Serializable