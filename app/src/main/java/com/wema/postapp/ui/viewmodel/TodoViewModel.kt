package com.wema.postapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wema.postapp.models.Todo
import com.wema.postapp.repository.TodoRepository
import com.wema.postapp.ui.viewmodel.state.TodoUiState
import kotlinx.coroutines.launch

class TodoViewModel(): ViewModel() {
    private val repository: TodoRepository = TodoRepository()
    private var _uiState = MutableLiveData<TodoUiState>()
    val uiState: LiveData<TodoUiState>
        get() = _uiState


    init {
        getTodos()
    }

    fun getTodos() {
        try {
            viewModelScope.launch {
                repository.fetchTodoFromApi { fetchTodosFromApi(it) }
            }
        } catch (e: Exception) {
            Log.e("PostViewModel", "Error fetching posts: ${e.message}")
        }
    }

    fun fetchTodosFromApi(response: List<Todo>) {
        _uiState.postValue(TodoUiState(response, false))
    }

    fun updateTodo(todoId: Int) {
        repository.updateTodo(todoId)
    }


}