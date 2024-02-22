package com.example.taskone.screens.first.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskone.data.models.posts.Post
import com.example.taskone.data.models.posts.PostsData
import com.example.taskone.data.services.api.ApiServiceClient
import com.example.taskone.data.services.api.PostsServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel : ViewModel() {
    var postsLiveData: MutableLiveData<List<Post>?> = MutableLiveData()
    private var size: Int = 0

    fun getLiveData(): MutableLiveData<List<Post>?> = postsLiveData

    fun getPosts() {
        val retrofitInstance =
            ApiServiceClient.createService(PostsServiceApi::class.java) as PostsServiceApi
        val retrofitData = retrofitInstance.getPostsData(size, 10)

        retrofitData.enqueue(object : Callback<PostsData?> {
            override fun onResponse(call: Call<PostsData?>, response: Response<PostsData?>) {
                postsLiveData.postValue(response.body()?.posts)
                size += 10
            }

            override fun onFailure(call: Call<PostsData?>, t: Throwable) {
                postsLiveData.postValue(null)
            }
        })
        ApiServiceClient.destroyInstance()
    }
}