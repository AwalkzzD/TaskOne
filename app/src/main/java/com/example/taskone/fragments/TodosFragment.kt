package com.example.taskone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.adapter.TodosDataAdapter
import com.example.taskone.database.AppDatabase
import com.example.taskone.databinding.FragmentTodosBinding
import com.example.taskone.viewmodels.TodosViewModel

class TodosFragment : Fragment() {
    private lateinit var todosBinding: FragmentTodosBinding
    private lateinit var todosDataAdapter: TodosDataAdapter
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
            savetoDB()
        }
    }

    private fun savetoDB() {
        val dbInstance = AppDatabase.getInstance(requireContext())
        val todosDao = dbInstance!!.todosDao()

        todosDao.insert(todosDataAdapter.getTodos())
    }

    private fun initRecyclerView() {
        todosDataAdapter = TodosDataAdapter(requireActivity())
        todosBinding.recyclerView.adapter = todosDataAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(TodosViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            todosDataAdapter.addTodos(it!!)
            todosDataAdapter.notifyDataSetChanged()
        }
        viewModel.getTodos()
    }
}