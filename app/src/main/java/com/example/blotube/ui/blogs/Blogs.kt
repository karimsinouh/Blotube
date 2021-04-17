package com.example.blotube.ui.blogs

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.blotube.data.blogger.Blog
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.showPost.ShowPostActivity
import com.example.blotube.ui.theme.CenterProgressBar

@Composable
fun Blogs(
    vm:MainViewModel,
){

    val c = LocalContext.current

    if (vm.postsLoading.value && vm.blogs.isEmpty() ){
        CenterProgressBar()
    }else{
        LazyColumn {
            itemsIndexed(vm.blogs){index,item->
                BlogItem(item){
                    showPost(c,item)
                }
                Divider()

                val canLoadMore=vm.blogs.isNotEmpty() && vm.postsNextPageToken!="" || vm.blogs.isEmpty() && vm.postsNextPageToken==""
                if ((index+1)==vm.blogs.size && !vm.postsLoading.value && canLoadMore){
                    vm.loadPosts()
                }

            }

            if (vm.postsLoading.value && vm.blogs.isNotEmpty()){
                item {
                    CenterProgressBar(false)
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }

}

fun showPost(c:Context,post:Blog){
    val i= Intent(c, ShowPostActivity::class.java)
    i.putExtra("post_id",post.id)
    c.startActivity(i)
}
