package com.example.taskone.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskone.api.PostsApi
import com.example.taskone.data.posts.Post
import com.example.taskone.data.posts.PostsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel : ViewModel() {
    var postsLiveData: MutableLiveData<List<Post>?> = MutableLiveData()
    private var size: Int = 0

    fun getLiveData(): MutableLiveData<List<Post>?> {
        return postsLiveData
    }

    fun getPosts() {
        val retrofitInstance = PostsApi.getInstance()
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
    }
}