package com.wema.postapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.wema.postapp.R
import com.wema.postapp.models.Post
import com.wema.postapp.ui.fragment.PostFragmentDirections

class PostAdapter(
    var posts : List<Post>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.itemView.apply {
            val postId = posts[position].id
            val title = posts[position].title
            val postTitle = this.findViewById<TextView>(R.id.tvPostTitle)
            postTitle.text = title

            val body = posts[position].body
            val postBody  = this.findViewById<TextView>(R.id.tvPostBody)
            postBody.text = body

            val image = posts[position]?.image
            val postImage =  this.findViewById<ImageView>(R.id.ivPostImage)
            if (image != null) {
               postImage.setImageResource(image)
            }

            this.setOnClickListener {
                val direction = PostFragmentDirections.actionPostFragmentToShowPostFragment(postId)
                findNavController().navigate(direction)
            }
        }


    }

    override fun getItemCount(): Int {
        return posts.size
    }
}