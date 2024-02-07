package com.example.taskone.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskone.api.ProductsApi
import com.example.taskone.data.Product
import com.example.taskone.data.ProductsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsViewModel : ViewModel() {

    lateinit var productsLiveData: MutableLiveData<List<Product>?>

    init {
        productsLiveData = MutableLiveData()
    }

    fun getLiveData(): MutableLiveData<List<Product>?> {
        return productsLiveData
    }

    fun getProducts(skip: Int, limit: Int) {
        val retrofitInstance = ProductsApi.getInstance()
        val retrofitData = retrofitInstance.getProductsData(skip, limit)

        retrofitData.enqueue(object : Callback<ProductsData?> {
            override fun onResponse(call: Call<ProductsData?>, response: Response<ProductsData?>) {
                productsLiveData.postValue(response.body()?.products!!)
            }

            override fun onFailure(call: Call<ProductsData?>, t: Throwable) {
                productsLiveData.postValue(null)
            }
        })

    }

}