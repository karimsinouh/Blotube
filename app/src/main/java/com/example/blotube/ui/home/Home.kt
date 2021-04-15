package com.example.blotube.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.blotube.R
import com.example.blotube.data.youtube.thumbnails.Thumbnails
import com.example.blotube.ui.blogs.BlogItem
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.main.Screen
import com.example.blotube.ui.playlists.PlaylistItem
import com.example.blotube.ui.theme.RoundedShape
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun Home(
    vm:MainViewModel,
    nav:NavController
){


    LazyColumn {

        //videos
        stickyHeader {
            Header(title = "Videos", actionText ="See All" ) {
                nav.navigate(Screen.ScreenVideos.root)
            }
        }

        item {

            val pagerState = if (vm.videos.size>10)
                rememberPagerState(pageCount = 10)
            else
                rememberPagerState(vm.videos.size)

            if(pagerState.pageCount >0)
                HorizontalPager(state = pagerState,modifier = Modifier.padding(8.dp).fillMaxWidth()) {page ->
                    val thumbs=vm.videos[page].snippet.thumbnails

                    val modifier=Modifier.fillMaxWidth(0.8f).height(150.dp).clip(RoundedShape).clickable {

                    }.graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    PagerVideoItem(thumbnails = thumbs,modifier=modifier)
                }
        }

        //playlists
        stickyHeader {
            Header(title = "Playlists", actionText ="See All" ) {
                nav.navigate(Screen.ScreenPlaylists.root)
            }
        }
        items(vm.playlists){item->
            PlaylistItem(item) {}
        }

        //blog posts

        stickyHeader {
            Header(title = "Blog Posts", actionText ="See All" ) {
                nav.navigate(Screen.ScreenBlogs.root)
            }
        }
        items(vm.blogs){item->
            BlogItem(item) {}
        }
    }
    
}

@Composable
fun Header(title:String,actionText:String,onAction:()->Unit){
    Box(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ){
        Row(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                title,
                fontSize = 18.sp,
                modifier= Modifier
                    .weight(0.9f)
                    .fillMaxWidth())

            TextButton(
                onClick = {
                    onAction()
                }
            ) {
                Text(text = actionText,color = MaterialTheme.colors.primary)
            }
        }
    }
}

@Composable
fun PagerVideoItem(thumbnails: Thumbnails,modifier: Modifier){

        Box(modifier = modifier,contentAlignment = Alignment.Center) {
            CoilImage(
                data=thumbnails.medium.url,
                contentDescription = "",
                Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(id = R.drawable.play_video),
                contentDescription = "",
                modifier=Modifier.size(50.dp)
            )
        }

}