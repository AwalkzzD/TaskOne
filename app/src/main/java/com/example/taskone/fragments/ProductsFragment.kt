package com.example.taskone.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.adapter.ProductsDataAdapter
import com.example.taskone.api.ProductsApi
import com.example.taskone.data.ProductsData
import com.example.taskone.databinding.FragmentProductsBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductsFragment : Fragment() {
    private val TAG = "ProductsFragment"
    private lateinit var productsBinding: FragmentProductsBinding
    private lateinit var productsDataAdapter: ProductsDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        productsBinding = FragmentProductsBinding.inflate(inflater)
        return productsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems(0, 15)

        val layoutManager = LinearLayoutManager(activity)
        productsBinding.recyclerView.layoutManager = layoutManager

        var loading = true
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        productsBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = false
                            loadItems(totalItemCount, 15)
                            loading = true
                        }
                    }
                }

                if (dy < 0)
                    Log.d(TAG, "onScrolled: Scroll Up Detected")
            }
        })
    }

    private fun loadItems(skip: Int, limit: Int) {
        val retrofitInstance = ProductsApi.getInstance()
        val retrofitData = retrofitInstance.getProductsData(skip, limit)

        retrofitData.enqueue(object : Callback<ProductsData?> {
            override fun onResponse(call: Call<ProductsData?>, response: Response<ProductsData?>) {
                Log.d(
                    TAG,
                    "onResponse: " + GsonBuilder().setPrettyPrinting().create()
                        .toJson(response.body())
                )

                productsDataAdapter = ProductsDataAdapter(activity!!, response.body()?.products!!)
                productsBinding.recyclerView.adapter = productsDataAdapter
                productsDataAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ProductsData?>, t: Throwable) {
                Log.d(TAG, "onFailure: --> $t")
            }
        })
    }
}