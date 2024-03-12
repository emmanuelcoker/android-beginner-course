package com.wema.postapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wema.postapp.R
import com.wema.postapp.databinding.FragmentCreatePostBinding
import com.wema.postapp.models.Post
import com.wema.postapp.ui.viewmodel.PostViewModel


class CreatePostFragment(val viewModel: PostViewModel) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreatePostBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCreatePostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wireUpEvent()
    }

    private fun wireUpEvent() {
        binding.mbAddPost.setOnClickListener {
            Toast.makeText(context, "Add button Clicked", Toast.LENGTH_SHORT).show()
            val title = binding.etPostTitle.text.toString()
            val body = binding.etPostBody.text.toString()
            if (title.isNotEmpty()){
                val post = Post(1, 1, title,body)
                viewModel?.addPost(post)
                resetForm()
            }

            dismiss()

        }
    }

    private fun resetForm() {
        binding.etPostTitle.setText("")
        binding.etPostBody.setText("")
    }

}