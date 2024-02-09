package com.example.taskone.data.services.api

import com.example.taskone.data.models.products.ProductsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsServiceApi {
    @GET("products")
    fun getProductsData(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
    ): Call<ProductsData>
}