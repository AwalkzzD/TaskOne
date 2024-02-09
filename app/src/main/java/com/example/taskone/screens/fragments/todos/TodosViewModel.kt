package com.example.taskone.screens.fragments.todos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskone.data.models.todos.Todo
import com.example.taskone.data.models.todos.TodosData
import com.example.taskone.data.services.api.ApiServiceClient
import com.example.taskone.data.services.api.TodosServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodosViewModel : ViewModel() {

    var todosLiveData: MutableLiveData<List<Todo>?> = MutableLiveData()
    private var size: Int = 0

    fun getLiveData(): MutableLiveData<List<Todo>?> {
        return todosLiveData
    }

    fun getTodos() {
        val retrofitInstance = ApiServiceClient.createService(TodosServiceApi::class.java)
        val retrofitData = retrofitInstance.getTodosData(size, 10)

        retrofitData.enqueue(object : Callback<TodosData?> {
            override fun onResponse(call: Call<TodosData?>, response: Response<TodosData?>) {
                todosLiveData.postValue(response.body()?.todos)
                size += 10
            }

            override fun onFailure(call: Call<TodosData?>, t: Throwable) {
                todosLiveData.postValue(null)
            }
        })
        ApiServiceClient.destroyInstance()
    }
}