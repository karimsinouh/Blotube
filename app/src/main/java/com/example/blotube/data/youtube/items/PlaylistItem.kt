package com.example.blotube.data.youtube.items

import com.example.blotube.data.youtube.ContentDetails
import com.example.blotube.data.youtube.Snippet
import java.io.Serializable

data class PlaylistItem(
    val kind:String,
    val etag:String,
    val snippet: Snippet,
    val id:String,
    val contentDetails: ContentDetails,
)