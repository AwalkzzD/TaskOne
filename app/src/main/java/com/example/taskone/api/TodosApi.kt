package com.example.taskone.api

import com.example.taskone.data.todos.TodosData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TodosApi {
    @GET("todos")
    fun getTodosData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<TodosData>

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        private var retrofitService: TodosApi? = null
        fun getInstance(): TodosApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(TodosApi::class.java)
            }
            return retrofitService!!
        }
    }
}