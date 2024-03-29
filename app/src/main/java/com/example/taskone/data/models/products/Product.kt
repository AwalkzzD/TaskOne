package com.example.taskone.data.models.products

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskone.data.models.ModelReferenceClass

@Entity(tableName = "products")
data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    @PrimaryKey
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
) : ModelReferenceClass()
