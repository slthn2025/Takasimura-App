package com.example.takasimura.ui.Fragment

import ExpanseAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takasimura.R
import com.example.takasimura.viewmodel.ExpanseListViewModel

class ExpanseList : Fragment() {

    private lateinit var expanseListViewModel: ExpanseListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpanseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_expanse_list, container, false)

        // Inisialisasi ViewModel
        expanseListViewModel = ViewModelProvider(requireActivity()).get(ExpanseListViewModel::class.java)

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi Adapter
        adapter = ExpanseAdapter()
        recyclerView.adapter = adapter

        // Fetch data dari ViewModel
        expanseListViewModel.fetchIncomeData()

        // Observe data dari ViewModel
        expanseListViewModel.ExpanseData.observe(requireActivity(), { data ->
            // Lakukan sesuatu dengan data yang diterima
            Log.d("Data Income", data.toString())
            adapter.updateData(data)
        })

        return view
    }
}