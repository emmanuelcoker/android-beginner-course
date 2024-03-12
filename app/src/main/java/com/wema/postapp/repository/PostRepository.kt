package com.wema.postapp.repository

import android.util.Log
import com.wema.postapp.R
import com.wema.postapp.models.Post
import com.wema.postapp.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class PostRepository {

    private var posts = listOf<Post>()

    fun allPosts(): List<Post> {
        return posts
    }

    fun createPost(post: Post) {
//        posts.add(post)
    }

    fun findPost(title: String): Post {
        val post = posts.filter {
            it.title == title
        }.first()

        return post
    }

     fun findPostFromApi(postId: Int, callback: (Post) -> Unit) {
        try {
            RetrofitClientInstance().getDataService()?.findPost(postId)?.enqueue(
                object : Callback<Post> {
                    override fun onResponse(call: Call<Post>, response: Response<Post>) {
                        if (response.isSuccessful){
                            val post = response.body()
                            if (post != null) {
                                callback(post)
                            }
                        }else{
                            Log.e("findPostFromApi", "Error finding post")
                        }
                    }

                    override fun onFailure(call: Call<Post>, t: Throwable) {
                        Log.e("findPostFromApi", t.message.toString())
                    }
                }
            )
        }catch (e: Exception) {
            Log.e("findPostFromApi", e.localizedMessage?.toString().toString())
        }
    }

    fun fetchPostsFromApi(callback: (List<Post>) -> Unit) {
        val postImages = listOf(R.drawable.wildlife,
                    R.drawable.trees, R.drawable.music,
                    R.drawable.street, R.drawable.agriculture)
        try {
            Log.d("fetchPostFromApi", "Fetching")
            RetrofitClientInstance()?.getDataService()?.getAllPosts()?.enqueue(
                    object: Callback<List<Post>> {
                        override fun onResponse(
                            call: Call<List<Post>>,
                            response: Response<List<Post>>
                        ) {
                            if (response.isSuccessful) {
                                posts = (response.body()?.map { post ->
                                    val randomImageIndex = Random.nextInt(postImages.size)
                                    Post(post.userId, post.id, post.title, post.body, postImages[randomImageIndex])
                                } ?: emptyList());
                                Log.d("PostResponse", "$response")
                                callback(posts)
                            }
                            else {
                                Log.e("fetchPostFromApi", "Something went wrong")
                            }

                        }
                        override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                            Log.e("fetchPostFromApi", t.message.toString()?: "Something went wrong")
                        }

                    }
            )
        }catch (e: Exception) {
            Log.e("PostRepository.fetch", e.localizedMessage?.toString().toString())
        }
    }

}