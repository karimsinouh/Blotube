package com.example.blotube.ui

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.blotube.ui.main.MainViewModel
import javax.xml.transform.OutputKeys.ENCODING

@Composable
fun ShowBlogPost(vm:MainViewModel,nav:NavController,blogId:String){



}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HtmlBlogView(data:String){

    return AndroidView (modifier = Modifier.clickable {  },factory = {context ->
        WebView(context).apply {

            settings.javaScriptEnabled=true
            settings.domStorageEnabled=true

            webViewClient=object :WebViewClient(){}
            webChromeClient=object :WebChromeClient(){}

            loadDataWithBaseURL(null,data,"text/html",ENCODING,null)

        }
    })
}