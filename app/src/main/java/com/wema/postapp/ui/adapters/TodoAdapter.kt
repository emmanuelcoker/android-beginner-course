package com.wema.postapp.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wema.postapp.R
import com.wema.postapp.models.Todo
import com.wema.postapp.ui.viewmodel.TodoViewModel

class TodoAdapter(private val todos: List<Todo>, private val viewModel : TodoViewModel) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            val todoTitle = this.findViewById<TextView>(R.id.tvTodoTitle)
            todoTitle.text = todos[position].title

            val todoStatus = this.findViewById<CheckBox>(R.id.cbTodoStatus)
            todoStatus.isChecked = todos[position].completed

            if(todoStatus.isChecked){
                todoTitle.setTextColor(getResources().getColor(R.color.gray))
            }else{
                todoTitle.setTextColor(getResources().getColor(R.color.black))
            }

            todoStatus.setOnClickListener {
                viewModel.updateTodo(todos[position].id)
                if(todoStatus.isChecked){
                    todoTitle.setTextColor(getResources().getColor(R.color.gray))
                }else{
                    todoTitle.setTextColor(getResources().getColor(R.color.black))
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return todos.size
    }
}