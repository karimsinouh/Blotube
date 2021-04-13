package com.example.blotube.ui.blogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.R
import com.example.blotube.data.blogger.Blog
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.ui.theme.Picture
import com.example.blotube.ui.theme.RoundedShape

@Composable
fun BlogItem(blog:Blog, onClick: ()->Unit){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable{onClick()}
    ) {

        val defPicture="https://www.androidcentral.com/sites/androidcentral.com/files/topic_images/2014/materialdesign_principles_metaphor.png"

        Picture(
            if(blog.images!=null) blog.images[0].url else defPicture,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedShape)
                .fillMaxWidth()
                .height(200.dp),
            onLoading = { CenterProgressBar()}
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = blog.title!!,
            fontSize = 20.sp,
            maxLines = 2
        )

        Text("by "+blog.author.displayName)


    }
}

@Composable
fun BlogItemSmall(blog:Blog){
    Row(Modifier.padding(8.dp)) {

        val defPicture="https://www.androidcentral.com/sites/androidcentral.com/files/topic_images/2014/materialdesign_principles_metaphor.png"

        Picture(
            if(blog.images!=null) blog.images[0].url else defPicture,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedShape)
                .width(150.dp)
                .height(90.dp),
            onLoading = { CenterProgressBar()}
        )

        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = blog.title!!,
                fontSize = 18.sp,
                maxLines = 2
            )
            Spacer(Modifier.height(4.dp))
            Text(blog.author.displayName)
        }

    }
}