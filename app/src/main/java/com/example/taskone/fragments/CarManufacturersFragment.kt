package com.example.taskone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskone.databinding.FragmentCarManufacturersBinding

class CarManufacturersFragment : Fragment() {

    private lateinit var carManufacturersBinding: FragmentCarManufacturersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carManufacturersBinding = FragmentCarManufacturersBinding.inflate(inflater)
        return carManufacturersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}