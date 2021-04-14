package com.example.blotube.ui.showPost

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.blogger.BlogsRepository
import com.example.blotube.data.blogger.Blog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowPostViewModel @Inject constructor(
    private val repo:BlogsRepository
):ViewModel() {

    val post= mutableStateOf<Blog?>(null)
    val message= mutableStateOf<String?>(null)
    val isLoading= mutableStateOf(true)

    fun loadPost(id:String)=viewModelScope.launch{

        if (post.value==null)
            repo.getPost(id){
                isLoading.value=false
                if(it.isSuccessful)
                    post.value=it.data
                else
                    message.value=it.message

            }

    }

}