package com.wema.postapp.models

data class Todo (
    val userId: Int,
    val id: Int,
    val title: String,
    var completed: Boolean
)