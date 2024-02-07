package com.example.taskone.api

import com.example.taskone.data.EmployeeData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeApi {
    @GET("employee")
    fun getProductsData(
        @Query("noofRecords") noofRecords: Int = 15,
        @Query("idStarts") idStarts: Int,
    ): Call<EmployeeData>

    companion object {
        private const val BASE_URL = "https://hub.dummyapis.com/"
        private var retrofitService: EmployeeApi? = null
        fun getInstance(): EmployeeApi {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(EmployeeApi::class.java)
            }
            return retrofitService!!
        }
    }
}