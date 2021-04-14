package com.example.blotube.ui

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.blotube.data.blogger.Blog
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar
import javax.xml.transform.OutputKeys.ENCODING

@Composable
fun ShowBlogPost(vm:MainViewModel,nav:NavController,postId:String){


    val post= remember { mutableStateOf<Blog?>(null) }
    val isLoading= remember { mutableStateOf(true) }

    vm.getPost(postId){
        isLoading.value=false
        post.value=it
    }


    if (isLoading.value)
        CenterProgressBar()
    else
        Column {
            val title=post.value?.title?.let {
                if(it.length>20)
                    it.substring(0,25)+"..."
                else
                    it
            }
            TopBar(title = title!!, nav = nav)
            HtmlPostView(data = post.value?.content!!)
        }


}


@Composable
private fun TopBar(title:String,nav:NavController){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground
    ) {
        IconButton(onClick = {
            nav.popBackStack()
        }) { Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "") }
        Text(title,fontSize = 16.sp)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HtmlPostView(data:String){

    return AndroidView (modifier = Modifier.fillMaxSize(

    ),factory = {context ->
        WebView(context).apply {

            settings.javaScriptEnabled=true
            settings.domStorageEnabled=true

            webViewClient=object :WebViewClient(){}
            webChromeClient=object :WebChromeClient(){}

            loadDataWithBaseURL(null,data,"text/html",ENCODING,null)

        }
    })
}