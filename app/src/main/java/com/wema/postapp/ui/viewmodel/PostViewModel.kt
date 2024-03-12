package com.wema.postapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wema.postapp.models.Post
import com.wema.postapp.repository.PostRepository
import com.wema.postapp.ui.viewmodel.state.PostUiState
import kotlinx.coroutines.launch

class PostViewModel() : ViewModel() {

    private var repository: PostRepository = PostRepository()
    private val _uiState = MutableLiveData<PostUiState>()
    val uiState: LiveData<PostUiState>
        get() = _uiState

    init {
        getPosts()
    }
    fun getPosts() {
            try {
                viewModelScope.launch {
                     repository.fetchPostsFromApi { getResponseFromApi(it) }
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error fetching posts: ${e.message}")
            }
    }

    private fun getResponseFromApi(response: List<Post>){
        _uiState.postValue(PostUiState(response, isLoading = false))
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            // Perform the addPost operation asynchronously
            repository.createPost(post)
            getPosts()
        }
    }

    //should be postId passed but pass title for now
    fun showPost(title: String) {
        viewModelScope.launch {
            try {
                val post = repository.findPost(title)
                val listPost = listOf(post)
                _uiState.postValue(PostUiState(listPost))
            } catch (e: Exception) {
                Log.e("ShowPost.ViewModel", "Error finding post: ${e.message}")
            }
        }
    }
}
