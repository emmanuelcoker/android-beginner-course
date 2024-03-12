package com.wema.postapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wema.postapp.databinding.FragmentTodoBinding
import com.wema.postapp.ui.adapters.TodoAdapter
import com.wema.postapp.ui.viewmodel.TodoViewModel
import com.wema.postapp.ui.viewmodel.state.TodoUiState

class TodoFragment : Fragment() {

    private lateinit var binding: FragmentTodoBinding

    private var viewModel: TodoViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(layoutInflater)

        try {
            viewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        }catch (e:Exception) {
            Log.e("TodoViewModel", "Error Instantiating TodoViewModel")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoObserver()
    }

    private fun todoObserver() {
        try {
            viewModel?.uiState?.observe(viewLifecycleOwner){ todoUiState ->
                if(todoUiState != null) {
                    setUpAdapter(todoUiState)
                }
            }
        }catch (e: Exception) {
            Log.e("TodoObserver", e.localizedMessage?.toString().toString())
        }
    }

    private fun setUpAdapter(todoUiState: TodoUiState) {
        val adapter = viewModel?.let { TodoAdapter(todoUiState.todos, it) }
        binding.rcTodos.adapter = adapter
        binding.rcTodos.layoutManager = LinearLayoutManager(activity)
        adapter?.notifyDataSetChanged()
    }

}