package com.example.taskone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskone.R
import com.example.taskone.database.AppDatabase
import com.example.taskone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.productsCount.text =
            AppDatabase.getInstance(requireContext())!!.productsDao().getCount().toString()
        homeBinding.todosCount.text =
            AppDatabase.getInstance(requireContext())!!.todosDao().getCount().toString()
        homeBinding.quotesCount.text =
            AppDatabase.getInstance(requireContext())!!.quotesDao().getCount().toString()

        homeBinding.todosButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_todosFragment)
        }
        homeBinding.carButton.setOnClickListener {}
        homeBinding.productsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_productsFragment)
        }
        homeBinding.quotesButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quotesFragment)
        }
    }
}