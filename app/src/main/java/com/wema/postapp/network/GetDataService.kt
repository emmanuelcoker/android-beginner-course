package com.wema.postapp.network

import com.wema.postapp.models.Post
import com.wema.postapp.models.Todo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetDataService {

    @GET("/posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("posts/{postId}")
    fun findPost(@Path("postId") postId: Int): Call<Post>

    @GET("/todos")
    fun getTodos(): Call<List<Todo>>
}
