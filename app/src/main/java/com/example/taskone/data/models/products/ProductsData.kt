package com.example.taskone.data.models.products

data class ProductsData(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)