package com.wema.postapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wema.postapp.databinding.FragmentPostBinding
import com.wema.postapp.models.Post
import com.wema.postapp.repository.PostRepository
import com.wema.postapp.ui.adapters.PostAdapter
import com.wema.postapp.ui.viewmodel.PostViewModel

class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private var
    viewModel: PostViewModel? = null
    private var adapter: PostAdapter = PostAdapter(emptyList())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater)
        try {
            viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        } catch (e: Exception) {
            Log.e("ViewModelProvider", e.localizedMessage?.toString().toString())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcPosts.adapter = adapter
        binding.rcPosts.layoutManager = LinearLayoutManager(activity)
        postObserver()
        wireUpPostAdapter()
        wireUpEvent()

    }

    private fun wireUpPostAdapter() {
        viewModel?.uiState?.value?.let { postUiState ->
            Log.d("MyApp", "PostUiState: $postUiState")
            setUpAdapter(postUiState.posts)
        }
    }

    private fun wireUpEvent() {
        binding.btnAddPost.setOnClickListener {
            viewModel?.let { it1 ->
                CreatePostFragment(it1).show(
                    requireActivity().supportFragmentManager,
                    "CreatePostDialogFragment"
                )
            }
        }
    }

    private fun setUpAdapter(posts: List<Post>) {
        adapter.posts = posts
        adapter.notifyDataSetChanged()
    }

    private fun postObserver() {
        try {
            viewModel?.uiState?.observe(viewLifecycleOwner) { postUiState ->
                postUiState?.let {
                    if(!it.isLoading) {
                        binding.progressBar.visibility = View.GONE
                    }else {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    setUpAdapter(it.posts)
                }
            }
        } catch (e: Exception) {
            Log.e("PostObserver", e.localizedMessage?.toString().toString())
        }
    }

}