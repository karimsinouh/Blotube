package com.example.blotube.ui.blogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.data.blogger.Author
import com.example.blotube.data.blogger.Blog
import com.example.blotube.ui.theme.ImagePlaceholder
import com.example.blotube.ui.theme.RoundedShape
import com.google.accompanist.coil.CoilImage

@Composable
fun BlogItem(blog:Blog, onClick: ()->Unit){

    Box(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }){

        Column(Modifier.padding(12.dp),verticalArrangement = Arrangement.spacedBy(4.dp)) {

            val defPicture="https://www.androidcentral.com/sites/androidcentral.com/files/topic_images/2014/materialdesign_principles_metaphor.png"

            CoilImage(
                data=blog.images!![0].url ?: defPicture,
                contentDescription="",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedShape),
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder() },
                fadeIn = true,
            )

            Text(
                text = blog.title!!,
                fontSize = 20.sp,
                maxLines = 2
            )

            Author(author = blog.author)


        }
    }

}

@Composable
fun BlogItemSmall(blog:Blog,onClick: () -> Unit){
    Box(Modifier.clickable { onClick() }) {
        Row(Modifier.padding(8.dp)) {

            val defPicture="https://www.androidcentral.com/sites/androidcentral.com/files/topic_images/2014/materialdesign_principles_metaphor.png"

            CoilImage(
                data=blog.images!![0].url ?: defPicture,
                contentDescription="",
                modifier = Modifier
                    .width(120.dp)
                    .height(80.dp)
                    .clip(RoundedShape),
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder() },
                fadeIn = true,
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
}

@Composable
fun Author(author:Author){

    val defPicture="https://www.androidcentral.com/sites/androidcentral.com/files/topic_images/2014/materialdesign_principles_metaphor.png"


    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically) {
        CoilImage(
            data="https:"+author.image?.url ?: defPicture,
            contentDescription="",
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            loading = { ImagePlaceholder() },
            fadeIn = true
            )


        Column {
            Text(author.displayName)
            Text(text = "2 days ago",fontSize = 10.sp)
        }
    }
}