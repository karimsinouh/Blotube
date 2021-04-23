package com.example.blotube.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.R

@Composable
fun Empty(
    text:String,
){
    Box(
        modifier=Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_box),
                contentDescription ="",
                tint=MaterialTheme.colors.onBackground.copy(alpha=0.85f)
            )

            Text(
                stringResource(R.string.empty_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text=text,
                modifier = Modifier.width(250.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground.copy(alpha=0.85f)
            )
        }
    }
}