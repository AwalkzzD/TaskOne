package com.example.taskone.data.models.todos

data class TodosData(
    val limit: Int,
    val skip: Int,
    val todos: List<Todo>,
    val total: Int
)