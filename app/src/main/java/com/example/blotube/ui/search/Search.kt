package com.example.blotube.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blotube.ui.theme.CenterProgressBar

@Composable
@Preview
fun Search(){
    val q=remember{ mutableStateOf("") }


    Column {

        Column(Modifier.padding(8.dp)) {
            SearchBar(value = q.value, onValueChange = { q.value=it }) {
                //TODO:LATER
            }
            FilterButtons(onReverseList = { /*TODO*/ }) {
                
            }
        }

        CenterProgressBar()

    }



}

