package com.example.blotube.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            
        }

    }
    
    
    @Composable
    @Preview
    fun Main(){
        Text(text = "ok ;let's see")
    }

    @Composable
    @Preview
    fun BottomBar(){

    }


}
