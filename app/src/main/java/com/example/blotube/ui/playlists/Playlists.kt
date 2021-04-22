package com.example.blotube.ui.playlists

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blotube.data.youtube.items.PlaylistItem
import com.example.blotube.data.youtube.items.SearchItem
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.playlistInfo.PlaylistInfoActivity
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.ui.theme.ImagePlaceholder
import com.example.blotube.ui.theme.RoundedShape
import com.google.accompanist.coil.CoilImage

@ExperimentalFoundationApi
@Composable
fun Playlists(vm:MainViewModel){

    val context= LocalContext.current

    if (vm.playlistsLoading.value && vm.videos.isEmpty() )
        CenterProgressBar()
    else
        LazyColumn {
            itemsIndexed(vm.playlists){index,item->
                PlaylistItem(item) {
                    showPlaylist(context,item)
                }

                val canLoadMore=vm.playlists.isNotEmpty() && vm.playlistsNextPageToken!="" || vm.playlists.isEmpty() && vm.playlistsNextPageToken==""
                if ((index+1)==vm.playlists.size && !vm.playlistsLoading.value && canLoadMore ){
                    vm.loadPlaylists()
                }

            }

            val isLoadingMore=vm.playlistsLoading.value && vm.playlists.isNotEmpty()
            if (isLoadingMore){
                item {
                    CenterProgressBar(false)
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }

        }

}

@Composable
fun PlaylistItem(playlist: PlaylistItem, onClick: () -> Unit){
    Box(Modifier.clickable { onClick() }) {
        Row(Modifier.padding(8.dp)) {

            CoilImage(
                data=playlist.snippet.thumbnails.medium.url,
                contentDescription="",
                modifier = Modifier
                    .width(140.dp)
                    .height(80.dp)
                    .clip(RoundedShape),
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder() }
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = playlist.snippet.title,
                    fontSize = 18.sp,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                Text(playlist.contentDetails.itemCount.toString())
            }

        }
    }
}


fun showPlaylist(c:Context, playlist: PlaylistItem){
    val intent=Intent(c,PlaylistInfoActivity::class.java).also {
        it.putExtra("playlist_id",playlist.id)
        it.putExtra("playlist_thumbnail",playlist.snippet.thumbnails.medium.url)
        it.putExtra("playlist_title",playlist.snippet.title)
    }
    c.startActivity(intent)
}

fun showSearchPlaylist(c:Context, playlist: SearchItem){
    val intent=Intent(c,PlaylistInfoActivity::class.java).also {
        it.putExtra("playlist_id",playlist.id.playlistId)
        it.putExtra("playlist_thumbnail",playlist.snippet.thumbnails.medium.url)
        it.putExtra("playlist_title",playlist.snippet.title)
    }
    c.startActivity(intent)
}