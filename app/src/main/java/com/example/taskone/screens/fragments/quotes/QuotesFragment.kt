package com.example.taskone.screens.fragments.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.screens.adapter.GenericBindingInterface
import com.example.taskone.screens.adapter.GenericDataAdapter
import com.example.taskone.data.models.quotes.Quote
import com.example.taskone.data.local.AppDatabase
import com.example.taskone.databinding.FragmentQuotesBinding

class QuotesFragment : Fragment() {

    private lateinit var quotesBinding: FragmentQuotesBinding
    private lateinit var genericDataAdapter: GenericDataAdapter<Quote>
    private lateinit var viewModel: QuotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
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
        quotesDao.insert(genericDataAdapter.getData())
    }

    private fun initRecyclerView() {
        val bindingInterface = object : GenericBindingInterface<Quote> {
            override fun bindData(item: Quote, itemView: View) {
                val quoteID: TextView = itemView.findViewById(R.id.quoteID)
                val quoteDetail: TextView = itemView.findViewById(R.id.quoteDetail)

                with(item) {
                    quoteID.text = this.id.toString()
                    quoteDetail.text = this.quote
                }
            }
        }
        genericDataAdapter =
            GenericDataAdapter(requireActivity(), R.layout.quote_list_item, bindingInterface)
        quotesBinding.recyclerView.adapter = genericDataAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(QuotesViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            genericDataAdapter.addData(it!!)
            genericDataAdapter.notifyDataSetChanged()
        }
        viewModel.getQuotes()
    }

}