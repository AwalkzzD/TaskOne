package com.example.taskone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskone.R
import com.example.taskone.databinding.FragmentCarModelBinding

class CarModelFragment : Fragment() {

    private lateinit var carModelBinding: FragmentCarModelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carModelBinding = FragmentCarModelBinding.inflate(inflater)
        return carModelBinding.root
    }
}