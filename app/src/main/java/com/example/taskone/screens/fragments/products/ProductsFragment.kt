package com.example.taskone.screens.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.local.AppDatabase
import com.example.taskone.data.models.products.Product
import com.example.taskone.databinding.FragmentProductsBinding
//import com.example.taskone.screens.adapter.GenericBindingInterface
import com.example.taskone.screens.adapter.GenericDataAdapter
import com.squareup.picasso.Picasso

class ProductsFragment : Fragment() {
    private lateinit var productsBinding: FragmentProductsBinding
    private lateinit var genericDataAdapter: GenericDataAdapter<Product>
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
            saveToDB()
        }
    }

    private fun saveToDB() {
        val dbInstance = AppDatabase.getInstance(requireContext())
        val productsDao = dbInstance!!.productsDao()
        productsDao.insert(genericDataAdapter.getData())
        Toast.makeText(
            requireActivity(),
            "${genericDataAdapter.itemCount} rows added",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initRecyclerView() {
        genericDataAdapter =
            GenericDataAdapter(requireActivity(), R.layout.product_list_item) { item, itemView ->

                val productThumbnail: ImageView = itemView.findViewById(R.id.productThumbnail)
                val productName: TextView = itemView.findViewById(R.id.productName)
                val productPrice: TextView = itemView.findViewById(R.id.productPrice)

                with(item) {
                    Picasso.get().load(this.thumbnail.toUri()).into(productThumbnail)
                    productName.text = this.title
                    productPrice.text = "${"$"}${this.price}"
                }

            }
        productsBinding.recyclerView.adapter = genericDataAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        viewModel.getProducts()
    }
}