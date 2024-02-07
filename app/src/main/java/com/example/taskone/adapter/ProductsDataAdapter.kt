package com.example.taskone.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.Product

class ProductsDataAdapter(private val context: Activity, private val dataList: List<Product>) :
    RecyclerView.Adapter<ProductsDataAdapter.ProductsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductsViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false)
        return ProductsViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: ProductsDataAdapter.ProductsViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                productID.text = this.id.toString()
                productName.text = this.title
                productPrice.text = this.price.toString()
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productID: TextView
        val productName: TextView
        val productPrice: TextView

        init {
            productID = itemView.findViewById(R.id.productID)
            productName = itemView.findViewById(R.id.productName)
            productPrice = itemView.findViewById(R.id.productPrice)
        }
    }

}
