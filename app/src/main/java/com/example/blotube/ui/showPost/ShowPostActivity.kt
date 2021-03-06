package com.example.blotube.ui.showPost

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.example.blotube.R
import com.example.blotube.ui.theme.BlotubeTheme
import com.example.blotube.ui.theme.CenterProgressBar
import com.example.blotube.util.NightMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShowPostActivity : ComponentActivity() {

    private val vm by viewModels<ShowPostViewModel>()
    private lateinit var postId:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Blotube_Splash)


        setContent {

            val nightMode=NightMode.isEnabled(this).collectAsState(initial = false)

            BlotubeTheme(nightMode.value) {

                window.statusBarColor=
                    if(nightMode.value)
                        MaterialTheme.colors.surface.toArgb()
                    else
                        MaterialTheme.colors.primaryVariant.toArgb()

                Surface (
                    color=MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onBackground
                ){

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
        }

        postId=intent.getStringExtra("post_id")!!
        vm.loadPost(postId)
    }

    @Composable
    @Preview
    private fun TopBar(){
        TopAppBar {

            IconButton(onClick = {
                finish()
            }) { Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "") }
            
            val title=vm.post.value?.title?.let {
                if(it.length>25)
                    it.substring(0,25)+"..."
                else
                    it
            } ?: ""

            

            Text(title,fontSize = 16.sp,modifier= Modifier
                .fillMaxWidth()
                .weight(0.7f))

            val exists=vm.exists(postId).observeAsState()


            IconToggleButton(checked = exists.value?:false, onCheckedChange = {
                vm.onReadLaterCheckChanges(it)
            }) {
                if (exists.value == true)
                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription = "")
                else
                    Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription ="" )
            }

            IconButton(onClick = {
                share()
            }) {
                Icon(imageVector = Icons.Outlined.Share, contentDescription ="" )
            }

        }
    }

    private fun share() {
        val intent=Intent().apply {
            type="text/plain"
            action=Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,vm.post.value?.url)

        }
        startActivity(intent)
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun HtmlPostView(data:String){

        return AndroidView (
            modifier = Modifier.fillMaxSize(),
            factory =
            {context ->
                WebView(context).apply {

                    settings.javaScriptEnabled=true

                    settings.domStorageEnabled=true
                    settings.loadWithOverviewMode=true

                    webViewClient=object : WebViewClient(){}
                    webChromeClient=object : WebChromeClient(){}

                    lifecycleScope.launch {
                        loadDataWithBaseURL(null,data,"text/html", "UTF-8",null)
                    }

            }
        })
    }
}

