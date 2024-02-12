package com.example.taskone.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskone.R
import com.example.taskone.data.local.AppDatabase
import com.example.taskone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCounts()

        homeBinding.todosButton.setOnClickListener {
//            val fragmentManager = parentFragmentManager
            /*val todoFragment = CommonFragment.newInstance<Todo>(
                ViewModelProvider(this).get(
                    TodosViewModel::class.java
                ), { item, itemView ->
                    val todoID: TextView = itemView.findViewById(R.id.todoID)
                    val todoDetail: TextView = itemView.findViewById(R.id.todoDetail)
                    val todoStatus: TextView = itemView.findViewById(R.id.todoStatus)

                    with(item) {
                        todoID.text = this.id.toString()
                        todoDetail.text = this.todo
                        todoStatus.text = this.completed.toString()
                    }
                }, R.layout.todo_list_item
            )*/
            /*fragmentManager.commit {

            }*/
            findNavController().navigate(R.id.action_homeFragment_to_todosFragment)
        }
        homeBinding.postsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postsFragment)
        }
        homeBinding.productsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_productsFragment)
        }
        homeBinding.quotesButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quotesFragment)
        }

        homeBinding.todoDelete.setOnClickListener {
            AppDatabase.getInstance(requireContext())!!.todosDao().deleteAllTodos()
            showToast("Todo Deleted")
        }
        homeBinding.postsDelete.setOnClickListener {
            AppDatabase.getInstance(requireContext())!!.postsDao().deleteAllPosts()
            showToast("Posts Deleted")
        }
        homeBinding.productsDelete.setOnClickListener {
            AppDatabase.getInstance(requireContext())!!.productsDao().deleteAllProducts()
            showToast("Products Deleted")
        }
        homeBinding.quotesDelete.setOnClickListener {
            AppDatabase.getInstance(requireContext())!!.quotesDao().deleteAllQuotes()
            showToast("Quotes Deleted")
        }
    }

    private fun setCounts() {
        homeBinding.todosCount.text =
            AppDatabase.getInstance(requireContext())!!.todosDao().getCount().toString()
        homeBinding.postsCount.text =
            AppDatabase.getInstance(requireContext())!!.postsDao().getCount().toString()
        homeBinding.productsCount.text =
            AppDatabase.getInstance(requireContext())!!.productsDao().getCount().toString()
        homeBinding.quotesCount.text =
            AppDatabase.getInstance(requireContext())!!.quotesDao().getCount().toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
        setCounts()
    }
}