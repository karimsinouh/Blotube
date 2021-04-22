package com.example.blotube.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.R
import com.example.blotube.data.blogger.Blog
import com.example.blotube.data.youtube.items.SearchItem
import com.example.blotube.data.youtube.items.VideoItem
import com.example.blotube.ui.theme.ImagePlaceholder
import com.example.blotube.ui.theme.RoundedShape
import com.google.accompanist.coil.CoilImage

@Composable
fun FilterButtons(
    onReverseList:()->Unit,
    onFilterClick:()->Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){

        Text(
            text = "Filter",
            modifier= Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .padding(4.dp)
        )

        IconButton(onClick = onReverseList) {
            Icon(imageVector = Icons.Outlined.FilterList, contentDescription ="" )
        }

        IconButton(onClick = onFilterClick) {
            Icon(imageVector = Icons.Outlined.FilterAlt, contentDescription = "")
        }
    }
}

@Composable
fun SearchBar(
    value:String,
    onValueChange:(String)->Unit,
    onSearchClick:()->Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = {onValueChange(it)},
        label={ Text(stringResource(R.string.search)) },
        placeholder = { Text(stringResource(R.string.search)) },
        trailingIcon = {
            IconButton(onClick = onSearchClick) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "")
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun VideoItemSmall(
    type:String?="youtube",
    youtube:SearchItem?=null,
    post:Blog?=null,
){

    Box(Modifier.clickable {  }) {
        Row(Modifier.padding(8.dp)) {

            CoilImage(
                data="video.snippet.thumbnails.medium.url",
                contentDescription="",
                modifier = Modifier
                    .width(140.dp)
                    .height(80.dp)
                    .clip(RoundedShape),
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder() }
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = "video.snippet.title",
                    fontSize = 18.sp,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                Text("video.snippet.publishedAt")
            }

        }
    }
}
