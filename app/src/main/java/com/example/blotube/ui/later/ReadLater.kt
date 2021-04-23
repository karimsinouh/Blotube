package com.example.blotube.ui.later

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.R
import com.example.blotube.api.database.Database
import com.example.blotube.api.database.entity.PostsEntity
import com.example.blotube.ui.blogs.showPost
import com.example.blotube.ui.theme.ImagePlaceholder
import com.example.blotube.ui.theme.RoundedShape
import com.example.blotube.util.Empty
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReadLater(db: Database){

    val posts=db.posts().getAll().observeAsState()
    val c= LocalContext.current

    posts.value?.let {
        if (it.isEmpty()){
            Empty(stringResource(R.string.empty_read_later_text))
        }else
            LazyColumn {
                items(it.reversed()){item->
                    ReadLaterItem(item){
                        showPost(c,item.id)
                    }
                }
            }
    }


}



@Composable
fun ReadLaterItem(
    blog: PostsEntity,
    onClick: () -> Unit
) {
    Box(Modifier.clickable { onClick() }) {

        Row(Modifier.padding(8.dp)) {

            CoilImage(
                data=blog.thumbnail,
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
                    text = blog.title,
                    fontSize = 18.sp,
                    maxLines = 3
                )
            }

        }
    }
}
