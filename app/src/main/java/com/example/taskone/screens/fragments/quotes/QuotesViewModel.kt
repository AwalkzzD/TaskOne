package com.example.taskone.screens.fragments.quotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskone.data.models.quotes.Quote
import com.example.taskone.data.models.quotes.QuotesData
import com.example.taskone.data.services.api.ApiServiceClient
import com.example.taskone.data.services.api.QuotesServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesViewModel : ViewModel() {
    var quotesLiveData: MutableLiveData<List<Quote>?> = MutableLiveData()
    private var size: Int = 0

    fun getLiveData(): MutableLiveData<List<Quote>?> {
        return quotesLiveData
    }

    fun getQuotes() {
        val retrofitInstance = ApiServiceClient.createService(QuotesServiceApi::class.java)
        val retrofitData = retrofitInstance.getQuotesData(size, 10)

        retrofitData.enqueue(object : Callback<QuotesData?> {
            override fun onResponse(call: Call<QuotesData?>, response: Response<QuotesData?>) {
                quotesLiveData.postValue(response.body()?.quotes)
                size += 10
            }

            override fun onFailure(call: Call<QuotesData?>, t: Throwable) {
                quotesLiveData.postValue(null)
            }
        })
        ApiServiceClient.destroyInstance()
    }
}