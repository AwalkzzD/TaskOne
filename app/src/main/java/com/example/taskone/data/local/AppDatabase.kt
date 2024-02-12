package com.example.taskone.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskone.data.local.dao.PostsDao
import com.example.taskone.data.local.dao.ProductsDao
import com.example.taskone.data.local.dao.QuotesDao
import com.example.taskone.data.local.dao.TodosDao
import com.example.taskone.data.local.utils.Converters
import com.example.taskone.data.models.posts.Post
import com.example.taskone.data.models.products.Product
import com.example.taskone.data.models.quotes.Quote
import com.example.taskone.data.models.todos.Todo

@Database(
    entities = [Product::class, Todo::class, Quote::class, Post::class], version = 5
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
    abstract fun todosDao(): TodosDao
    abstract fun quotesDao(): QuotesDao
    abstract fun postsDao(): PostsDao


    companion object {
        private var dbInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "test"
                ).allowMainThreadQueries().build()
            }
            return dbInstance
        }
    }
}
