package com.example.taskone.data.models.todos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    val completed: Boolean,
    @PrimaryKey
    val id: Int,
    val todo: String,
    val userId: Int
)