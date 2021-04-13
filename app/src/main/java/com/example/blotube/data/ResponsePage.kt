package com.example.blotube.data

data class ResponsePage<T>(
    val kind:String,
    val nextPageToken:String?="",
    val prevPageToken:String?="",
    val items:T,
)
