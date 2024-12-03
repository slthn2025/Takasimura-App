package com.example.takasimura.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.CalendarView
import com.example.takasimura.R

class Calendar : Fragment() {

    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Inisialisasi MaterialCalendarView
        calendarView = view.findViewById(R.id.calendarView)

        // Tambahkan listener untuk memilih tanggal
//        calendarView.setOnDateChangedListener { widget, date, selected ->
//            // Logika untuk menangani tanggal yang dipilih
//            // Anda dapat menampilkan tanggal atau melakukan tindakan lain
//        }

        return view
    }


}
