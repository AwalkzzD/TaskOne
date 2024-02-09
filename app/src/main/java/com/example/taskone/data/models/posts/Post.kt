package com.example.taskone.data.models.posts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    val body: String,
    @PrimaryKey
    val id: Int,
    val reactions: Int,
    val tags: List<String>,
    val title: String,
    val userId: Int
)