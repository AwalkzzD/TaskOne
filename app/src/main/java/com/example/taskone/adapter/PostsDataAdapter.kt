package com.example.taskone.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.posts.Post

class PostsDataAdapter(private val context: Activity) :
    RecyclerView.Adapter<PostsDataAdapter.PostsViewHolder>() {

    private var postsList: MutableList<Post> = emptyList<Post>().toMutableList()

    fun addPosts(posts: List<Post>) {
        this.postsList.addAll(posts)
    }

    fun getPosts(): List<Post> {
        return postsList.toList()
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postID: TextView
        val postTitle: TextView
        val postReactions: TextView

        init {
            postID = itemView.findViewById(R.id.postID)
            postTitle = itemView.findViewById(R.id.postTitle)
            postReactions = itemView.findViewById(R.id.postReactions)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false)
        return PostsViewHolder(itemView)
    }

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        with(holder) {
            with(postsList[position]) {
                postID.text = this.id.toString()
                postTitle.text = this.title
                postReactions.text = "${this.reactions} Likes"
            }
        }
    }
}