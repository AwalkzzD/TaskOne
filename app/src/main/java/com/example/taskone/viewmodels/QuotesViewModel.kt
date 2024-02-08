package com.example.taskone.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskone.api.QuotesApi
import com.example.taskone.data.quotes.Quote
import com.example.taskone.data.quotes.QuotesData
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
        val retrofitInstance = QuotesApi.getInstance()
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
    }
}