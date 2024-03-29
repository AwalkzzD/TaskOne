package com.example.taskone.screens.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class GenericDataAdapter<T : Any>(
    private val context: Activity,
    @LayoutRes val layoutID: Int,
    private val bindFunction: (item: T, itemView: View) -> Unit
) : RecyclerView.Adapter<GenericDataAdapter.ViewHolder>() {

    private var dataList: MutableList<T> = emptyList<T>().toMutableList()

    fun addData(data: List<T>) {
        this.dataList.addAll(data)
    }

    fun getData(): List<T> {
        return dataList.toList()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(layoutID, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        bindFunction(item, holder.itemView)
    }
}