package com.example.blotube.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.blotube.ui.main.Screen

@ExperimentalMaterialApi
@Composable
fun Drawer(
    currentRoute:String,
    onClick: (Screen) -> Unit
){

    val drawerScreens= listOf(
        Screen.ScreenHome,
        Screen.ScreenWatchLater,
        Screen.ScreenReadLater,
        Screen.ScreenSettings
    )

    Column {
        drawerScreens.forEach {
            val selected=it.root==currentRoute
            DrawerRow(
                text = stringResource(id = it.label),
                icon =it.icon,
                isSelected = selected
            ) {
                onClick(it)
            }
        }
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