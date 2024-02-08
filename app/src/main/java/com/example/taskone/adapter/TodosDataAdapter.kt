package com.example.taskone.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.todos.Todo

class TodosDataAdapter(private val context: Activity) :
    RecyclerView.Adapter<TodosDataAdapter.TodosViewHolder>() {

    private var todoList: MutableList<Todo> = emptyList<Todo>().toMutableList()

    fun addTodos(users: List<Todo>) {
        this.todoList.addAll(users)
    }

    fun getTodos(): List<Todo> {
        return todoList.toList()
    }

    class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoID: TextView
        val todoDetail: TextView
        val todoStatus: TextView

        init {
            todoID = itemView.findViewById(R.id.todoID)
            todoDetail = itemView.findViewById(R.id.todoDetail)
            todoStatus = itemView.findViewById(R.id.todoStatus)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false)
        return TodosViewHolder((itemView))
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        with(holder) {
            with(todoList[position]) {
                todoID.text = this.id.toString()
                todoDetail.text = this.todo
                todoStatus.text = this.completed.toString()
            }
        }
    }
}