package com.example.blotube.ui.showPost

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.blotube.ui.theme.BlotubeTheme
import com.example.blotube.ui.theme.CenterProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.xml.transform.OutputKeys


@AndroidEntryPoint
class ShowPostActivity : ComponentActivity() {

    private val vm by viewModels<ShowPostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlotubeTheme {
                
                Column {


                    if(vm.isLoading.value){
                        CenterProgressBar()
                    }else{
                        TopBar()
                        HtmlPostView(data = vm.post.value?.content!!)
                    }

                }
                
            }
        }

        val postId=intent.getStringExtra("post_id")!!
        vm.loadPost(postId)
    }

    @Composable
    @Preview
    private fun TopBar(){
        TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 1.dp
        ) {
            IconButton(onClick = {
                finish()
            }) { Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "") }
            
            val title=vm.post.value?.title?.let {
                if(it.length>20)
                    it.substring(0,25)+"..."
                else
                    it
            } ?: ""

            

            Text(title,fontSize = 16.sp,modifier= Modifier
                .fillMaxWidth()
                .weight(0.8f))

            val checked= mutableStateOf(false)

            IconToggleButton(checked = false, onCheckedChange = {
                checked.value = !checked.value
            }) {
                if (checked.value)
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                else
                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription ="" )
            }

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

                webViewClient=object : WebViewClient(){}
                webChromeClient=object : WebChromeClient(){}

                loadDataWithBaseURL(null,data,"text/html", OutputKeys.ENCODING,null)

            }
        })
    }
}

