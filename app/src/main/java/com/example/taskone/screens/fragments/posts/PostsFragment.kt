package com.example.taskone.screens.fragments.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.local.AppDatabase
import com.example.taskone.data.models.posts.Post
import com.example.taskone.databinding.FragmentPostsBinding
import com.example.taskone.screens.adapter.GenericDataAdapter

class PostsFragment : Fragment() {
    private lateinit var postsBinding: FragmentPostsBinding
    private lateinit var genericDataAdapter: GenericDataAdapter<Post>
    private lateinit var viewModel: PostsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
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
        postsDao.insert(genericDataAdapter.getData())
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(PostsViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        viewModel.getPosts()
    }

    private fun initRecyclerView() {
        genericDataAdapter =
            GenericDataAdapter(requireActivity(), R.layout.post_list_item) { item, itemView ->

                val postID: TextView = itemView.findViewById(R.id.postID)
                val postTitle: TextView = itemView.findViewById(R.id.postTitle)
                val postReactions: TextView = itemView.findViewById(R.id.postReactions)

                with(item) {
                    postID.text = this.id.toString()
                    postTitle.text = this.title
                    postReactions.text = "${this.reactions} Likes"
                }

            }
        postsBinding.recyclerView.adapter = genericDataAdapter
    }

}