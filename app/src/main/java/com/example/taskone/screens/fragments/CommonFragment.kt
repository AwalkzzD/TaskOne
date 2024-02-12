package com.example.taskone.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.databinding.FragmentCommonBinding

class CommonFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    private lateinit var binding: FragmentCommonBinding

    /*
            private lateinit var bindFunction: (item: T, itemView: View) -> Unit
        private lateinit var genericDataAdapter: GenericDataAdapter<T>*/
    private var layoutID: Int? = null/*

    companion object {
        fun <T : Any> newInstance(
            viewModel: ViewModel,
            bindFunction: (item: T, itemView: View) -> Unit,
            @LayoutRes layoutID: Int,
        ): CommonFragment<T> {
            val fragment = CommonFragment<T>()
            fragment.viewModel = viewModel
            fragment.bindFunction = bindFunction
            fragment.layoutID = layoutID
            return fragment
        }
    }
*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommonBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && !binding.recyclerView.canScrollVertically(1)) {

                }
            }
        })

    }

    private fun initRecyclerView() {/*        genericDataAdapter =
                    GenericDataAdapter(requireActivity(), R.layout.todo_list_item, bindFunction)
                binding.recyclerView.adapter = genericDataAdapter*/
    }


    private fun initViewModel() {

    }

}