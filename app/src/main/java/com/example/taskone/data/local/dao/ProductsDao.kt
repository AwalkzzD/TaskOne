package com.example.taskone.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskone.data.models.products.Product

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<Product>)

    @Query("SELECT COUNT(id) FROM products")
    fun getCount(): Int

    @Query("DELETE FROM products")
    fun deleteAllProducts()
}