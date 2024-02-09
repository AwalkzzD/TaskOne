package com.example.taskone.data.models.posts

data class PostsData(
    val limit: Int,
    val posts: List<Post>,
    val skip: Int,
    val total: Int
)