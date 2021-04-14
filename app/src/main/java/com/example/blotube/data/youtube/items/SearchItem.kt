package com.example.blotube.data.youtube.items

import com.example.blotube.data.youtube.ResourceId
import com.example.blotube.data.youtube.Snippet

data class SearchItem (
    val kind:String,
    val etag:String,
    val snippet: Snippet,
    val id: ResourceId,
)