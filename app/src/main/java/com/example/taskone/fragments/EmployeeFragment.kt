package com.example.taskone.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskone.api.EmployeeApi
import com.example.taskone.data.EmployeeData
import com.example.taskone.databinding.FragmentEmployeeBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeFragment : Fragment() {
    private val TAG = "EmployeeFragment"
    private lateinit var employeeBinding: FragmentEmployeeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeeBinding = FragmentEmployeeBinding.inflate(inflater)
        return employeeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitInstance = EmployeeApi.getInstance()

        val retrofitData = retrofitInstance.getProductsData(20, 1)
        retrofitData.enqueue(object : Callback<EmployeeData?> {
            override fun onResponse(call: Call<EmployeeData?>, response: Response<EmployeeData?>) {
                Log.d(
                    TAG,
                    "onResponse: " + GsonBuilder().setPrettyPrinting().create()
                        .toJson(response.body())
                )
            }

            override fun onFailure(call: Call<EmployeeData?>, t: Throwable) {
                Log.d(TAG, "onFailure: --> $t")
            }
        })

    }
}