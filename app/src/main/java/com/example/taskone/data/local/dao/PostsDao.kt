package com.example.taskone.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskone.data.models.posts.Post

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Post>)

    @Query("SELECT COUNT(id) from posts")
    fun getCount(): Int

    @Query("DELETE FROM posts")
    fun deleteAllPosts()
}