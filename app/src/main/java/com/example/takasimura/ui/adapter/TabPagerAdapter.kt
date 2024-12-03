package com.example.takasimura.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.takasimura.ui.Fragment.ExpenseFragment
import com.example.takasimura.ui.Fragment.IncomeFragment

class TabPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2 // Jumlah tab

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IncomeFragment() // Tab pertama
            1 -> ExpenseFragment() // Tab kedua
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}