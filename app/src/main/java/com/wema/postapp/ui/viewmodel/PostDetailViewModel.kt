package com.wema.postapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wema.postapp.models.Post
import com.wema.postapp.repository.PostRepository
import com.wema.postapp.ui.viewmodel.state.PostDetailUiState
import com.wema.postapp.ui.viewmodel.state.PostUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(): ViewModel() {
    private val repository: PostRepository = PostRepository()
    private var _postState = MutableStateFlow<PostDetailUiState>(PostDetailUiState())
    val postState: StateFlow<PostDetailUiState> = _postState.asStateFlow()

    private var fetchJob: Job? = null


    fun findPost(postId: Int) {
        Log.d("findPost", "findPost called")

        try {
            viewModelScope.launch {
                repository.findPostFromApi(postId){ post ->
                    run {
                        Log.d("findPost", "running findPos")
                        getResponseFromApi(post)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("PostViewModel", "Error fetching posts: ${e.message}")
        }
    }

    private fun getResponseFromApi(post: Post){
        Log.d("getResponseFromApi", "running findPos")

        _postState.value = PostDetailUiState(post?.userId, post?.id, post?.title, post?.body, post?.image)
    }

//    fun findPost(postId: Int) {
//        Log.d("fetchJob", "find post called")
//        fetchJob?.cancel()
//        fetchJob =  viewModelScope.launch {
//            try {
//                Log.d("fetchJob", "fetching job")
//                val post = repository.findPostFromApi(postId)
//                _postState.value =
//                Log.d("fetchJob Post", "$post")
//            } catch (e: Exception) {
//                // Handle the error and notify the UI when appropriate.
//                Log.e("findPost", e.localizedMessage?.toString().toString())
//            }
//        }
//    }

}