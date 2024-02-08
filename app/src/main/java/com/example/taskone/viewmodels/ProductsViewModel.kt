package com.example.taskone.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskone.api.ProductsApi
import com.example.taskone.data.products.Product
import com.example.taskone.data.products.ProductsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsViewModel : ViewModel() {

    var productsLiveData: MutableLiveData<List<Product>?> = MutableLiveData()
    private var size: Int = 0

    fun getLiveData(): MutableLiveData<List<Product>?> {
        return productsLiveData
    }

    fun getProducts() {
        val retrofitInstance = ProductsApi.getInstance()
        val retrofitData = retrofitInstance.getProductsData(size, 10)

        retrofitData.enqueue(object : Callback<ProductsData?> {
            override fun onResponse(call: Call<ProductsData?>, response: Response<ProductsData?>) {
                productsLiveData.postValue(response.body()?.products)
                size += 10
            }

            override fun onFailure(call: Call<ProductsData?>, t: Throwable) {
                productsLiveData.postValue(null)
            }
        })
    }
}