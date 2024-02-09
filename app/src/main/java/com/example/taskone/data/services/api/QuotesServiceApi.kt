package com.example.taskone.data.services.api

import com.example.taskone.data.models.quotes.QuotesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesServiceApi {
    @GET("quotes")
    fun getQuotesData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<QuotesData>
}