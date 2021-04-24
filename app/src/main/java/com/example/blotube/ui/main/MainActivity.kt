package com.example.blotube.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.blotube.R
import com.example.blotube.api.database.Database
import com.example.blotube.ui.blogs.Blogs
import com.example.blotube.ui.drawer.Drawer
import com.example.blotube.ui.home.Home
import com.example.blotube.ui.later.ReadLater
import com.example.blotube.ui.later.WatchLater
import com.example.blotube.ui.playlists.Playlists
import com.example.blotube.ui.search.Search
import com.example.blotube.ui.settings.Settings
import com.example.blotube.ui.theme.BlotubeTheme
import com.example.blotube.ui.theme.DrawerShape
import com.example.blotube.ui.videos.Videos
import com.example.blotube.util.NightMode
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var navController:NavHostController
    private lateinit var navBackStackEntry: State<NavBackStackEntry?>
    private lateinit var currentRoot:String
    private lateinit var scaffoldState: ScaffoldState
    private lateinit var scope:CoroutineScope

    private val vm by viewModels<MainViewModel>()

    @Inject
    lateinit var database:Database

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            scaffoldState= rememberScaffoldState(DrawerState(DrawerValue.Closed))
            navController= rememberNavController()
            navBackStackEntry=navController.currentBackStackEntryAsState()
            currentRoot=navBackStackEntry.value?.arguments?.get(KEY_ROUTE).toString()

            scope= rememberCoroutineScope()

            val shouldShowBottomNav=vm.menuItems.containsRoot(currentRoot)

            val nightMode=NightMode.isEnabled(this).collectAsState(initial = false)

            BlotubeTheme(nightMode.value){

                window.statusBarColor=MaterialTheme.colors.primaryVariant.toArgb()

                Scaffold(
                    bottomBar = { BottomBar(shouldShowBottomNav) },
                    topBar = {TopBar(shouldShowBottomNav)},
                    drawerContent = {
                        Drawer(currentRoot){
                            navigate(it.root)
                            closeDrawer()
                        } },
                    drawerShape = DrawerShape,
                    scaffoldState = scaffoldState,
                ) {
                    Content(shouldShowBottomNav)
                }

            }

            vm.message.value?.let {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it,actionLabel = getString(R.string.ok))
                }
                vm.message.value=null
            }

        }

    }


    @Composable
    private fun TopBar(visible: Boolean){
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            elevation = 1.dp
        ) {

            if(visible)
                IconButton(onClick = {
                    openDrawer()
                }) { Icon(imageVector = Icons.Outlined.Menu, contentDescription = "") }
            else
                IconButton(onClick = {
                    navController.popBackStack()
                }) { Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "") }

            Text(stringResource(id = R.string.app_name),fontSize = 16.sp)
        }
    }

    private fun openDrawer()= scope.launch {
            scaffoldState.drawerState.open()
        }

    private fun closeDrawer()=scope.launch {
        scaffoldState.drawerState.close()
    }


    /**
     * param makePadding: it makes padding in the bottom of the screen
     * so the BottomNav doesn't cover the content
     * it's used only in the screens that contain BottomNav
     * */
    @ExperimentalFoundationApi
    @Composable
    private fun Content(makePadding:Boolean)=
        NavHost(
            navController = navController,
            startDestination = Screen.ScreenHome.root,
            modifier= if (makePadding) Modifier.padding(PaddingValues(0.dp,0.dp,0.dp,57.dp)) else Modifier
            ){

            //Bottom Navigation
            composable(Screen.ScreenHome.root){ Home(vm,navController) }
            composable(Screen.ScreenVideos.root){ Videos(vm) }
            composable(Screen.ScreenSearch.root){ Search(vm) }
            composable(Screen.ScreenPlaylists.root){ Playlists(vm) }
            composable(Screen.ScreenBlogs.root){ Blogs(vm) }

            //Drawer
            composable(Screen.ScreenWatchLater.root){ WatchLater(database) }
            composable(Screen.ScreenReadLater.root){ ReadLater(database) }
            composable(Screen.ScreenSettings.root){ Settings(database) }
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
                        label = { Text(text = getString(it.label),maxLines=1)},
                        icon = { Icon(imageVector = it.icon, contentDescription = "") },
                        alwaysShowLabel = false,
                        onClick = {navigate(it.root)}
                    )
                }
            }

    }
    private fun navigate(route:String){
        navController.navigate(route){
            popUpTo=navController.graph.startDestination
            launchSingleTop=true
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
