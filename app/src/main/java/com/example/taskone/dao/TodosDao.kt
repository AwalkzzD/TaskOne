package com.example.taskone.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskone.data.todos.Todo

@Dao
interface TodosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todos: List<Todo>)

    @Query("SELECT COUNT(id) FROM todos")
    fun getCount(): Int
}