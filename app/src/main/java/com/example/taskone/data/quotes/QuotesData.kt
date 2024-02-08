package com.example.taskone.data.quotes

data class QuotesData(
    val limit: Int,
    val quotes: List<Quote>,
    val skip: Int,
    val total: Int
)