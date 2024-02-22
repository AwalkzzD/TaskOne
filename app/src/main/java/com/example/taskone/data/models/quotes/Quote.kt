package com.example.taskone.data.models.quotes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskone.data.models.ModelReferenceClass

@Entity(tableName = "quotes")
data class Quote(
    val author: String,
    @PrimaryKey
    val id: Int,
    val quote: String
) : ModelReferenceClass()