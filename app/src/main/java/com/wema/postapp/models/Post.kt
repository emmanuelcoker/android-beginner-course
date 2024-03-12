package com.wema.postapp.models

data class Post (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val image: Int? = null
)