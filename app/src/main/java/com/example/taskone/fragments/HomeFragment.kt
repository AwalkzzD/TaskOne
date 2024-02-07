package com.example.taskone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskone.R
import com.example.taskone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.employeeButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_employeeFragment)
        }
        homeBinding.carButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_carModelFragment)
        }
        homeBinding.productsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_productsFragment)
        }
        homeBinding.companyButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_carManufacturersFragment)
        }
    }
}