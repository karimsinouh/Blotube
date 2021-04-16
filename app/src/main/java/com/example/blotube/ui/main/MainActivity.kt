package com.example.blotube.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.blotube.ui.blogs.Blogs
import com.example.blotube.ui.drawer.Drawer
import com.example.blotube.ui.home.Home
import com.example.blotube.ui.playlists.Playlists
import com.example.blotube.ui.search.Search
import com.example.blotube.ui.theme.BlotubeTheme
import com.example.blotube.ui.theme.DrawerShape
import com.example.blotube.ui.theme.RoundedShape
import com.example.blotube.ui.videos.Videos
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var navController:NavHostController
    private lateinit var navBackStackEntry: State<NavBackStackEntry?>
    private lateinit var currentRoot:String
    private lateinit var scaffoldState: ScaffoldState

    private val vm by viewModels<MainViewModel>()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            scaffoldState= rememberScaffoldState(DrawerState(DrawerValue.Closed))
            navController= rememberNavController()
            navBackStackEntry=navController.currentBackStackEntryAsState()
            currentRoot=navBackStackEntry.value?.arguments?.get(KEY_ROUTE).toString()

            val shouldShowMenu=vm.menuItems.containsRoot(currentRoot)

            BlotubeTheme{
                Scaffold(
                    bottomBar = { BottomBar(shouldShowMenu) },
                    topBar = {TopBar(shouldShowMenu)},
                    drawerContent = { Drawer() },
                    drawerShape = DrawerShape,
                    scaffoldState = scaffoldState
                ) {
                    Content()
                }
            }

        }

    }


    @Composable
    private fun TopBar(visible: Boolean){
        if (visible)
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                elevation = 1.dp
            ) {

                IconButton(onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        scaffoldState.drawerState.open()
                    }
                }) { Icon(imageVector = Icons.Outlined.Menu, contentDescription = "") }

                Text(text = "Blotube",fontSize = 16.sp)
            }
    }
    
    @ExperimentalFoundationApi
    @Composable
    @Preview
    private fun Content()=
        NavHost(navController = navController,startDestination = Screen.ScreenHome.root){
            composable(Screen.ScreenHome.root){ Home(vm,navController) }
            composable(Screen.ScreenVideos.root){ Videos(vm) }
            composable(Screen.ScreenSearch.root){ Search() }
            composable(Screen.ScreenPlaylists.root){ Playlists(vm) }
            composable(Screen.ScreenBlogs.root){ Blogs(vm) }
        }




    @Composable
    private fun BottomBar(visible:Boolean){

        if (visible)
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

    private fun List<Screen>.containsRoot(root:String):Boolean{
        for(screen in this){
            if (screen.root==root)
                return true
        }
        return false
    }

}
