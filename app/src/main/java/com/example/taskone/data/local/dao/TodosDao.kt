package com.example.taskone.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskone.data.models.todos.Todo

@Dao
interface TodosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todos: List<Todo>)

    @Query("SELECT COUNT(id) FROM todos")
    fun getCount(): Int

    @Query("DELETE FROM todos")
    fun deleteAllTodos()
}