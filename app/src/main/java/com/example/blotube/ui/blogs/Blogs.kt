package com.example.blotube.ui.blogs

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.blotube.data.blogger.Blog
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.showPost.ShowPostActivity
import com.example.blotube.ui.theme.CenterProgressBar

@Composable
fun Blogs(
    vm:MainViewModel,
    c: Context,
){

    if (vm.postsLoading.value){
        CenterProgressBar()
    }else{
        LazyColumn {
            items(vm.blogs){item->
                BlogItem(item){
                    showPost(c,item)
                }
                Divider()
            }
        }
    }

}

private fun showPost(c:Context,post:Blog){
    val i= Intent(c, ShowPostActivity::class.java)
    i.putExtra("post_id",post.id)
    c.startActivity(i)
}
