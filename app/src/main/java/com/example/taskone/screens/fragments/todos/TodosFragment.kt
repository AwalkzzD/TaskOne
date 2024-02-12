package com.example.taskone.screens.fragments.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.local.AppDatabase
import com.example.taskone.data.models.todos.Todo
import com.example.taskone.databinding.FragmentTodosBinding
import com.example.taskone.screens.adapter.GenericDataAdapter

class TodosFragment : Fragment() {
    private lateinit var todosBinding: FragmentTodosBinding
    private lateinit var genericDataAdapter: GenericDataAdapter<Todo>
    private lateinit var viewModel: TodosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        todosBinding = FragmentTodosBinding.inflate(inflater)
        return todosBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()

        todosBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        todosBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !todosBinding.recyclerView.canScrollVertically(1)) {
                    viewModel.getTodos()
                }
            }
        })

        todosBinding.addToRoomDB.setOnClickListener {
            saveToDB()
        }
    }

    private fun saveToDB() {
        val dbInstance = AppDatabase.getInstance(requireContext())
        val todosDao = dbInstance!!.todosDao()
        todosDao.insert(genericDataAdapter.getData())
        Toast.makeText(
            requireActivity(),
            "${genericDataAdapter.itemCount} rows added",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initRecyclerView() {
        genericDataAdapter =
            GenericDataAdapter(requireActivity(), R.layout.todo_list_item) { item, itemView ->

                val todoID: TextView = itemView.findViewById(R.id.todoID)
                val todoDetail: TextView = itemView.findViewById(R.id.todoDetail)
                val todoStatus: TextView = itemView.findViewById(R.id.todoStatus)

                with(item) {
                    todoID.text = this.id.toString()
                    todoDetail.text = this.todo
                    todoStatus.text = this.completed.toString()
                }
            }
        todosBinding.recyclerView.adapter = genericDataAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[TodosViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        viewModel.getTodos()
    }
}