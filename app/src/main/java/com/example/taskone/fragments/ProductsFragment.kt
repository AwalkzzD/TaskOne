package com.example.taskone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.adapter.ProductsDataAdapter
import com.example.taskone.database.AppDatabase
import com.example.taskone.databinding.FragmentProductsBinding
import com.example.taskone.viewmodels.ProductsViewModel

class ProductsFragment : Fragment() {
    private lateinit var productsBinding: FragmentProductsBinding
    private lateinit var productsDataAdapter: ProductsDataAdapter
    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        productsBinding = FragmentProductsBinding.inflate(inflater)
        return productsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()

        productsBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        productsBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !productsBinding.recyclerView.canScrollVertically(1)) {
                    viewModel.getProducts()
                }
            }
        })

        productsBinding.addToRoomDB.setOnClickListener {
            savetoDB()
        }
    }

    private fun savetoDB() {
        val dbInstance = AppDatabase.getInstance(requireContext())
        val productsDao = dbInstance!!.productsDao()

        productsDao.insert(productsDataAdapter.getProducts())
    }

    private fun initRecyclerView() {
        productsDataAdapter = ProductsDataAdapter(requireActivity())
        productsBinding.recyclerView.adapter = productsDataAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            productsDataAdapter.addProduct(it!!)
            productsDataAdapter.notifyDataSetChanged()
        }
        viewModel.getProducts()
    }
}