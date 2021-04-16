package com.example.blotube.data.youtube.thumbnails

import java.io.Serializable

data class Thumbnails(
    val default: Thumbnail,
    val medium: Thumbnail,
    val high: Thumbnail,
):Serializable