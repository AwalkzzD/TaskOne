package com.example.taskone.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.adapter.ProductsDataAdapter
import com.example.taskone.databinding.FragmentProductsBinding
import com.example.taskone.viewmodels.ProductsViewModel


class ProductsFragment : Fragment() {
    private val TAG = "ProductsFragment"
    private lateinit var productsBinding: FragmentProductsBinding
    private lateinit var productsDataAdapter: ProductsDataAdapter
    private var dataSize: Int = 0
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        productsBinding = FragmentProductsBinding.inflate(inflater)
        return productsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        loadItems(0, 15)
        initRecyclerView()
        initViewModel()
        viewModel.getProducts(dataSize, 15)


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
                            /*loadItems(totalItemCount, 15)*/
                            viewModel.getProducts(dataSize, 15)
                            loading = true
                        }
                    }
                }

                if (dy < 0)
                    Log.d(TAG, "onScrolled: Scroll Up Detected")
            }
        })
    }

    private fun initRecyclerView() {
        productsDataAdapter = ProductsDataAdapter(requireActivity())
        productsBinding.recyclerView.adapter = productsDataAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            dataSize = it!!.size
            productsDataAdapter.addProduct(it.toMutableList())
            productsDataAdapter.notifyDataSetChanged()
        })
        viewModel.getProducts(dataSize, 15)
    }

    /*private fun loadItems(skip: Int, limit: Int) {
        val retrofitInstance = ProductsApi.getInstance()
        val retrofitData = retrofitInstance.getProductsData(skip, limit)

        retrofitData.enqueue(object : Callback<ProductsData?> {
            override fun onResponse(call: Call<ProductsData?>, response: Response<ProductsData?>) {
                Log.d(
                    TAG,
                    "onResponse: " + GsonBuilder().setPrettyPrinting().create()
                        .toJson(response.body())
                )

                productsDataAdapter = ProductsDataAdapter(activity!!)
                productsBinding.recyclerView.adapter = productsDataAdapter
                productsDataAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ProductsData?>, t: Throwable) {
                Log.d(TAG, "onFailure: --> $t")
            }
        })
    }*/
}