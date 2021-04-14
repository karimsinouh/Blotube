package com.example.blotube.data.youtube.items

import com.example.blotube.data.youtube.ContentDetails
import com.example.blotube.data.youtube.Snippet
import com.example.blotube.data.youtube.Statistics

data class VideoItem (
    val kind:String,
    val etag:String,
    val snippet: Snippet,
    val id:String?=null,
    val contentDetails: ContentDetails?=null,
    val statistics: Statistics?=null
)
