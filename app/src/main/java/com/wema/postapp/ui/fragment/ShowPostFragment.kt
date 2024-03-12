package com.wema.postapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.wema.postapp.databinding.FragmentShowPostBinding
import com.wema.postapp.ui.viewmodel.PostDetailViewModel
import com.wema.postapp.ui.viewmodel.PostViewModel
import com.wema.postapp.ui.viewmodel.state.PostDetailUiState
import kotlinx.coroutines.launch


class ShowPostFragment() : Fragment() {
   private lateinit var binding: FragmentShowPostBinding
   private var viewModel: PostViewModel? = null
   private var postDetailViewModel: PostDetailViewModel? = null
    private val arguments: ShowPostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowPostBinding.inflate(layoutInflater)
        try {
            viewModel = ViewModelProvider(this)[PostViewModel::class.java]
            postDetailViewModel = ViewModelProvider(this)[PostDetailViewModel::class.java]
        } catch (e: Exception) {
            Log.e("ViewModelProvider", e.localizedMessage?.toString().toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postObserver()

        //get arguments
        val postId = arguments.postId
        Log.d("postId", "$postId")
        if (postId != null) {
            Log.d("findPost", "finding post")
            postDetailViewModel?.findPost(postId)
        }
    }

    private fun postObserver() {
        try {
//            viewModel?.uiState?.observe(viewLifecycleOwner) { postUiState ->
//                postUiState?.let{
//                    val postId = arguments?.getString("title")
//                    val post = postUiState.posts.first { it.title == postId }
//                    binding.tvPostDetailTitle.text = post.title
//
//                    binding.tvPostDetailBody.text = post.body
//                    post.image?.let { it1 -> binding.ivPostDetailImage.setImageResource(it1) }
//                }
//            }
            Log.d("post Observing", "observing")

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    postDetailViewModel?.postState?.collect { post ->
                        binding.tvPostDetailTitle.text = post.title
                        binding.tvPostDetailBody.text = post.body
                        Log.d("post Observing", "$post")
                        post.image?.let { it1 -> binding.ivPostDetailImage.setImageResource(it1) }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("PostObserver", e.localizedMessage?.toString().toString())
        }
    }

}