package com.wema.postapp.repository

import android.util.Log
import com.wema.postapp.models.Todo
import com.wema.postapp.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TodoRepository {
    private lateinit var todos: List<Todo>

    init {
        dummyTodos()
    }

    private fun dummyTodos() {
        todos =  listOf(
            Todo(1, 1, "Todo Item 1", true),
            Todo(1, 1, "Todo Item 2", false),
            Todo(1, 1, "Todo Item 3", false),
            Todo(1, 1, "Todo Item 4", true),
            Todo(1, 1, "Todo Item 5", false),
        )
    }

    fun getTodos(): List<Todo> {
        return todos
    }

    fun updateTodo(todoId: Int) {
        todos.find { it.id == todoId}?.completed = !todos.find { it.id == todoId}?.completed!!
    }

    fun fetchTodoFromApi(callback:(List<Todo>) -> Unit) {
        try {
            RetrofitClientInstance?.getInstance()?.getDataService()?.getTodos()?.enqueue(
                object : Callback<List<Todo>> {
                    override fun onResponse(
                        call: Call<List<Todo>>,
                        response: Response<List<Todo>>
                    ) {
                        if (response.isSuccessful) {
                            todos = response.body()?.toList() ?: emptyList()
                            Log.d("Todos", "Todos loaded")
                            callback(todos)
                        }else{
                            Log.e("Response Not Successful", "Something went wrong")
                        }
                    }

                    override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                        Log.e("Error fetching Todos", t.message.toString())
                    }
                }
            )

        }catch (e: Exception){
            Log.e("fetchTodoFromApi", e.localizedMessage?.toString().toString())
        }
    }
}