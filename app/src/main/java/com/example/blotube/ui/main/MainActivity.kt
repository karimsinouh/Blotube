package com.example.blotube.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.blotube.ui.blogs.Blogs
import com.example.blotube.ui.home.Home
import com.example.blotube.ui.playlists.Playlists
import com.example.blotube.ui.search.Search
import com.example.blotube.ui.videos.Videos

class MainActivity : ComponentActivity() {


    private lateinit var navController:NavHostController
    private lateinit var navBackStackEntry: State<NavBackStackEntry?>
    private lateinit var currentRoot:String

    private val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController= rememberNavController()
            navBackStackEntry=navController.currentBackStackEntryAsState()
            currentRoot=navBackStackEntry.value?.arguments?.get(KEY_ROUTE).toString()
            Log.d("wtf",currentRoot)

            Scaffold(
                bottomBar = { BottomBar() },
                topBar = {TopBar()}
            ) {
                Content()
            }

        }

    }


    @Composable
    @Preview
    private fun TopBar(){
        TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 1.dp
        ) {
            val showBackButton=vm.menuItems.contains(currentRoot)
            if (showBackButton)
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "")
                }
            else
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Outlined.Menu, contentDescription = "")
                }

            Text(text = "Blotube",fontSize = 16.sp)
        }
    }
    
    @Composable
    @Preview
    private fun Content()=Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)) {

        NavHost(navController = navController,startDestination = Screen.ScreenHome.root){
            composable(Screen.ScreenHome.root){ Home() }
            composable(Screen.ScreenVideos.root){ Videos() }
            composable(Screen.ScreenSearch.root){ Search() }
            composable(Screen.ScreenPlaylists.root){ Playlists() }
            composable(Screen.ScreenBlogs.root){ Blogs() }
        }

    }


    @Composable
    private fun BottomBar(){

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground
        ) {
            vm.menuItems.forEach {
                BottomNavigationItem(
                    selected = currentRoot==it.root,
                    onClick = { navController.navigate(it.root) },
                    label = { Text(text = getString(it.label),maxLines=1)},
                    icon = { Icon(imageVector = it.icon, contentDescription = "") },
                    alwaysShowLabel = false,
                )
            }
        }

    }

}
