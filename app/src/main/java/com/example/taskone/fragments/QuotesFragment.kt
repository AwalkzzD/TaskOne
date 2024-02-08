package com.example.taskone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.adapter.QuotesDataAdapter
import com.example.taskone.database.AppDatabase
import com.example.taskone.databinding.FragmentQuotesBinding
import com.example.taskone.viewmodels.QuotesViewModel

class QuotesFragment : Fragment() {

    private lateinit var quotesBinding: FragmentQuotesBinding
    private lateinit var quotesDataAdapter: QuotesDataAdapter
    private lateinit var viewModel: QuotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        quotesBinding = FragmentQuotesBinding.inflate(inflater)
        return quotesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()

        quotesBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        quotesBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !quotesBinding.recyclerView.canScrollVertically(1)) {
                    viewModel.getQuotes()
                }
            }
        })

        quotesBinding.addToRoomDB.setOnClickListener {
            savetoDB()
        }
    }

    private fun savetoDB() {
        val dbInstance = AppDatabase.getInstance(requireContext())
        val quotesDao = dbInstance!!.quotesDao()

        quotesDao.insert(quotesDataAdapter.getQuotes())
    }

    private fun initRecyclerView() {
        quotesDataAdapter = QuotesDataAdapter(requireActivity())
        quotesBinding.recyclerView.adapter = quotesDataAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(QuotesViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            quotesDataAdapter.addQuotes(it!!)
            quotesDataAdapter.notifyDataSetChanged()
        }
        viewModel.getQuotes()
    }

}