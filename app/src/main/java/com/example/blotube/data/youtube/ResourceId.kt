package com.example.blotube.data.youtube

import java.io.Serializable

data class ResourceId(
    val kind:String,
    val videoId:String?="",
    val playlistId: String?=""
):Serializable