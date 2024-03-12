package com.wema.postapp.ui.viewmodel.state

import com.wema.postapp.models.Todo

data class TodoUiState (
    val todos: List<Todo>,
    var isCompleted: Boolean,
)