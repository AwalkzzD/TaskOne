package com.example.taskone.api

import com.example.taskone.data.ProductsData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {
    @GET("products")
    fun getProductsData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<ProductsData>

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        private var retrofitService: ProductsApi? = null
        fun getInstance(): ProductsApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ProductsApi::class.java)
            }
            return retrofitService!!
        }
    }
}