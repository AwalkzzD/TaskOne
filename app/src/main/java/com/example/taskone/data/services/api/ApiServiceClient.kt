package com.example.taskone.data.services.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceClient {
    companion object {
        private val BASE_URL = "https://dummyjson.com/"
        private var retrofitService: Any? = null
        fun <S> createService(serviceClass: Class<S>?): Any? {
            if (retrofitService == null) {
                val retrofit: Retrofit =
                    Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                retrofitService = retrofit.create(serviceClass!!)
            }
            return retrofitService
        }

        fun destroyInstance() {
            retrofitService = null
        }
    }

}