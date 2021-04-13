package com.example.blotube.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CenterProgressBar(){
    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
        CircularProgressIndicator(strokeWidth = 3.dp)
    }
}

@Composable
fun ImagePlaceholder(){
    Box(Modifier.background(PlaceholderColor))
}