package com.example.takasimura.ui.Fragment

import TransaksiAdapter
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
import com.example.takasimura.viewmodel.IncomeListViewModel

class Transaksi : Fragment() {

    private lateinit var incomeListViewModel: IncomeListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransaksiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)

        // Inisialisasi ViewModel
        incomeListViewModel = ViewModelProvider(requireActivity()).get(IncomeListViewModel::class.java)

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi Adapter
        adapter = TransaksiAdapter()
        recyclerView.adapter = adapter

        // Fetch data dari ViewModel
        incomeListViewModel.fetchIncomeData()

        // Observe data dari ViewModel
        incomeListViewModel.incomeData.observe(requireActivity(), { data ->
            // Lakukan sesuatu dengan data yang diterima
            Log.d("Data Income", data.toString())
            adapter.updateData(data)
        })

        return view
    }
}