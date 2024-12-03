package com.example.takasimura.ui.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.takasimura.R
import com.example.takasimura.ui.Activity.NoteTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Transaksi : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)

        // Ambil referensi FloatingActionButton
        val addButton: FloatingActionButton = view.findViewById(R.id.addButton)

        // Set OnClickListener untuk tombol addButton
        addButton.setOnClickListener {
            // Intent untuk berpindah ke NoteTransaction Activity
            val intent = Intent(activity, NoteTransaction::class.java)
            startActivity(intent)
        }

        return view
    }

}
