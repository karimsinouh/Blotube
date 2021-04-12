package com.example.blotube.ui.blogs

import androidx.compose.foundation.Image
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
import com.example.blotube.ui.theme.RoundedShape

@Composable
@Preview
fun BlogItem(){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Image(
            painterResource(id = R.drawable.image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedShape)
                .fillMaxWidth()
                .height(180.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "How to make a lazy column in jetpack compose",
            fontSize = 20.sp,
            maxLines = 2
        )

        Text(
            text="4 days ago"
        )


    }
}

@Composable
@Preview
fun BlogItemSmall(){
    Row(Modifier.padding(8.dp)) {
        Image(
            painterResource(id = R.drawable.image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedShape)
                .width(150.dp)
                .height(90.dp)
        )

        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = "How to make a lazy column in jetpack compose",
                fontSize = 18.sp,
                maxLines = 2
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text="4 days ago",
            )
        }

    }
}