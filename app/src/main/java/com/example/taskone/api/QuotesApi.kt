package com.example.taskone.api

import com.example.taskone.data.quotes.QuotesData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi : TodosApi {
    @GET("quotes")
    fun getQuotesData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<QuotesData>

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        private var retrofitService: QuotesApi? = null
        fun getInstance(): QuotesApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(QuotesApi::class.java)
            }
            return retrofitService!!
        }
    }
}