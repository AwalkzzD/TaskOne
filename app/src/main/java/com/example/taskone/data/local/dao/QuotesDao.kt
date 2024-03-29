package com.example.taskone.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskone.data.models.quotes.Quote

@Dao
interface QuotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todos: List<Quote>)

    @Query("SELECT COUNT(id) FROM quotes")
    fun getCount(): Int

    @Query("DELETE FROM quotes")
    fun deleteAllQuotes()
}