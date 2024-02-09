package com.example.taskone.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.products.Product
import com.squareup.picasso.Picasso

class ProductsDataAdapter(private val context: Activity) :
    RecyclerView.Adapter<ProductsDataAdapter.ProductsViewHolder>() {

    private var productList: MutableList<Product> = emptyList<Product>().toMutableList()

    fun addProduct(products: List<Product>) {
        this.productList.addAll(products)
    }

    fun getProducts(): List<Product> {
        return productList.toList()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductsViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false)
        return ProductsViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        with(holder) {
            with(productList[position]) {
                Picasso.get().load(this.thumbnail.toUri()).into(productThumbnail)
                productName.text = this.title
                productPrice.text = "$" + this.price.toString()
            }
        }
    }

    override fun getItemCount(): Int = productList.size

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productThumbnail: ImageView
        val productName: TextView
        val productPrice: TextView

        init {
            productThumbnail = itemView.findViewById(R.id.productThumbnail)
            productName = itemView.findViewById(R.id.productName)
            productPrice = itemView.findViewById(R.id.productPrice)
        }
    }

}
