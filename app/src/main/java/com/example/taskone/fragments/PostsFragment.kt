package com.example.taskone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.adapter.PostsDataAdapter
import com.example.taskone.database.AppDatabase
import com.example.taskone.databinding.FragmentPostsBinding
import com.example.taskone.viewmodels.PostsViewModel

class PostsFragment : Fragment() {

    private lateinit var postsBinding: FragmentPostsBinding
    private lateinit var postsDataAdapter: PostsDataAdapter
    private lateinit var viewModel: PostsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        postsBinding = FragmentPostsBinding.inflate(inflater)
        return postsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()

        postsBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        postsBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !postsBinding.recyclerView.canScrollVertically(1)) {
                    viewModel.getPosts()
                }
            }
        })

        postsBinding.addToRoomDB.setOnClickListener {
            savetoDB()
        }
    }

    private fun savetoDB() {
        val dbInstance = AppDatabase.getInstance(requireContext())
        val postsDao = dbInstance!!.postsDao()

        postsDao.insert(postsDataAdapter.getPosts())
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(PostsViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            postsDataAdapter.addPosts(it!!)
            postsDataAdapter.notifyDataSetChanged()
        }
        viewModel.getPosts()
    }

    private fun initRecyclerView() {
        postsDataAdapter = PostsDataAdapter(requireActivity())
        postsBinding.recyclerView.adapter = postsDataAdapter
    }

}