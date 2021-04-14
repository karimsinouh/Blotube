package com.example.blotube.data.youtube

data class ResourceId(
    val kind:String,
    val videoId:String?="",
    val playlistId: String?=""
)
