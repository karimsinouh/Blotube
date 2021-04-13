package com.example.blotube.ui

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.blotube.ui.main.MainViewModel
import com.example.blotube.ui.theme.CenterProgressBar
import javax.xml.transform.OutputKeys.ENCODING

@Composable
fun ShowBlogPost(vm:MainViewModel,nav:NavController,postId:String){


    val content= remember { mutableStateOf<String?>(null) }
    val isLoading= remember { mutableStateOf(true) }

    vm.getPost(postId){
        isLoading.value=false
        content.value=it.content
    }

    if (isLoading.value)
        CenterProgressBar()
    else
        HtmlPostView(data = content.value!!)


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