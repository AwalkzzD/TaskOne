package com.example.taskone.data.services.api

import com.example.taskone.data.models.todos.TodosData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TodosServiceApi {
    @GET("todos")
    fun getTodosData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<TodosData>
}