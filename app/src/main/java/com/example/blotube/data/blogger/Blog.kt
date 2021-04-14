package com.example.blotube.data.blogger

data class Blog(
    val id:String?="",
    val kind:String?="",
    val title:String?="",
    val content:String?="",
    val published:String?="",
    val updated:String?="",
    val url:String?="",
    val author: Author,
    val blog:BlogId,
    val images:List<BlogImage>?=null
)