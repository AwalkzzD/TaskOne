package com.example.taskone.api

import com.example.taskone.data.posts.PostsData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {
    @GET("posts")
    fun getPostsData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<PostsData>

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        private var retrofitService: PostsApi? = null

        fun getInstance(): PostsApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofitService = retrofit.create(PostsApi::class.java)
            }
            return retrofitService!!
        }
    }
}