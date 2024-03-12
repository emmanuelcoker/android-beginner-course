package com.wema.postapp.ui.viewmodel.state

import com.wema.postapp.models.Post

data class PostUiState (
    val posts: List<Post>,
    val isLoading: Boolean = false,
    val ErrorMessages: String? = null
    )
