package com.example.blotube.ui.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blotube.R

@ExperimentalMaterialApi
@Composable
@Preview
fun Drawer(){

    Column {
        Image(painter = painterResource(id = R.drawable.drawer_header),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().height(180.dp),
            contentScale = ContentScale.Crop
        )

        DrawerRow(text = "Home", icon =Icons.Outlined.Home) {}
        DrawerRow(text = "Watch Later", icon =Icons.Outlined.WatchLater,true) {}
        DrawerRow(text = "Settings", icon =Icons.Outlined.Settings) {}
        DrawerRow(text = "Favorites", icon =Icons.Outlined.FavoriteBorder) {}
    }

}

@ExperimentalMaterialApi
@Composable
fun DrawerRow(
    text:String,
    icon:ImageVector,
    isSelected:Boolean?=false,
    onClick:()->Unit
){

    val contentColor=if (isSelected!!) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface

    val backgroundColor=if (isSelected)
        MaterialTheme.colors.primary.copy(0.12f)
    else
        Color.Transparent

    ListItem(
        modifier= Modifier
            .background(backgroundColor)
            .clickable { onClick() },
        icon = { Icon(imageVector = icon, contentDescription = "",tint=contentColor)}
    ) {
        Text(text,color = contentColor)
    }
}