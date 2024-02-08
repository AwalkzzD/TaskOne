package com.example.taskone.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.data.quotes.Quote

class QuotesDataAdapter(private val context: Activity) :
    RecyclerView.Adapter<QuotesDataAdapter.QuotesViewHolder>() {

    private var quoteList: MutableList<Quote> = emptyList<Quote>().toMutableList()

    fun addQuotes(quotes: List<Quote>) {
        this.quoteList.addAll(quotes)
    }

    fun getQuotes(): List<Quote> {
        return quoteList.toList()
    }

    class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quoteID: TextView
        val quoteDetail: TextView

        init {
            quoteID = itemView.findViewById(R.id.quoteID)
            quoteDetail = itemView.findViewById(R.id.quoteDetail)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): QuotesViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.quote_list_item, parent, false)
        return QuotesViewHolder((itemView))
    }

    override fun onBindViewHolder(holder: QuotesDataAdapter.QuotesViewHolder, position: Int) {
        with(holder) {
            with(quoteList[position]) {
                quoteID.text = this.id.toString()
                quoteDetail.text = this.quote
            }
        }
    }

    override fun getItemCount(): Int = quoteList.size
}