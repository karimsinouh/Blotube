package com.example.blotube.ui.showPost

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blotube.api.blogger.BlogsRepository
import com.example.blotube.api.database.Database
import com.example.blotube.api.database.entity.PostsEntity
import com.example.blotube.data.blogger.Blog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowPostViewModel @Inject constructor(
    private val repo:BlogsRepository,
    private val database:Database
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

    fun exists(id:String)=database.posts().exists(id)

    fun onReadLaterCheckChanges(checked:Boolean){
        post.value?.let {
            if (checked){
                addToReadLater(it)
            }else {
                removeFromReadLater(it.id!!)
            }
        }
    }

    fun addToReadLater(post:Blog)=viewModelScope.launch{
        val item=PostsEntity(post.title!!,post.images!![0].url,post.id!!)
        database.posts().insert(item)
    }

    fun removeFromReadLater(id:String)=viewModelScope.launch{
        database.posts().delete(id)
    }

}