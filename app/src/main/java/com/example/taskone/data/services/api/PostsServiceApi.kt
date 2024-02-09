package com.example.taskone.data.services.api

import com.example.taskone.data.models.posts.PostsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsServiceApi {
    @GET("posts")
    fun getPostsData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<PostsData>
}