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
import com.example.blotube.util.asDate
import com.google.accompanist.coil.CoilImage

@Composable
fun BlogItem(blog:Blog, onClick: ()->Unit){

    Box(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }){

        Column(Modifier.padding(12.dp),verticalArrangement = Arrangement.spacedBy(4.dp)) {


            CoilImage(
                data=blog.images!![0].url ,
                contentDescription="",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedShape),
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder() }
            )

            Text(
                text = blog.title!!,
                fontSize = 20.sp,
                maxLines = 2
            )

            Author(author = blog.author,publishedAt = blog.published)


        }
    }

}

@Composable
fun BlogItemSmall(blog:Blog,onClick: () -> Unit){
    Box(Modifier.clickable { onClick() }) {
        Row(Modifier.padding(8.dp)) {

            CoilImage(
                data=blog.images!![0].url,
                contentDescription="",
                modifier = Modifier
                    .width(120.dp)
                    .height(80.dp)
                    .clip(RoundedShape),
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder() }
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
fun Author(author:Author,publishedAt:String?){

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically) {
        CoilImage(
            data="https:"+author.image?.url,
            contentDescription="",
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            loading = { ImagePlaceholder() }
            )


        Column {
            Text(author.displayName)
            Text(text = publishedAt.asDate(),fontSize = 10.sp)
        }
    }
}