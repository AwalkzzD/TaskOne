package com.example.taskone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskone.Converters
import com.example.taskone.dao.PostsDao
import com.example.taskone.dao.ProductsDao
import com.example.taskone.dao.QuotesDao
import com.example.taskone.dao.TodosDao
import com.example.taskone.data.posts.Post
import com.example.taskone.data.products.Product
import com.example.taskone.data.quotes.Quote
import com.example.taskone.data.todos.Todo

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
