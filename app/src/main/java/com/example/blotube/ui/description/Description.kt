package com.example.blotube.ui.description

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.data.youtube.Snippet
import com.example.blotube.util.asDate


@Composable
fun DescriptionDialog(snippet: Snippet){


   Column(
       Modifier
           .padding(12.dp)
           .verticalScroll(rememberScrollState())
   ) {
       Text(snippet.title,fontSize = 24.sp)
       Text(snippet.publishedAt.asDate(),fontSize=12.sp)
       Spacer(modifier = Modifier.height(6.dp))
       Divider()
       Spacer(modifier = Modifier.height(12.dp))

       Text(snippet.description)
   }
}