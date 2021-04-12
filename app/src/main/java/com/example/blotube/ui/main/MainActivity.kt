package com.example.blotube.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.sharp.VideoCall
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController

class MainActivity : ComponentActivity() {


    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController= rememberNavController()

            Main()

        }

    }
    
    
    @Composable
    @Preview
    private fun Main(){

        Scaffold(
            bottomBar = { BottomBar() }
        ) {

        }

    }

    @Composable
    @Preview
    private fun BottomBar(){

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoot=navBackStackEntry?.arguments?.get(KEY_ROUTE)

        val items:List<Screen> = listOf(
            Screen.ScreenHome,
            Screen.ScreenVideos,
            Screen.ScreenSearch,
            Screen.ScreenPlaylists,
            Screen.ScreenBlogs
        )

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground
        ) {
            items.forEach {
                BottomNavigationItem(
                    selected = currentRoot==it.root,
                    onClick = {  },
                    label = { Text(text = getString(it.label))},
                    icon = { Icon(imageVector = it.icon, contentDescription = "") },
                    alwaysShowLabel = false,
                )
            }
        }

    }

}
