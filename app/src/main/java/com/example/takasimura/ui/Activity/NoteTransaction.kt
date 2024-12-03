package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.takasimura.R
import com.example.takasimura.ui.adapter.TabPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NoteTransaction : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note_transaction)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        val iconScan = findViewById<ImageView>(R.id.iconScan)
        iconScan.setOnClickListener {
            // Intent untuk membuka ScanActivity
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

        val iconSearch = findViewById<ImageView>(R.id.iconSearch)
        iconSearch.setOnClickListener {
            // Intent untuk membuka ScanActivity
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        // Set adapter for ViewPager2
        viewPager.adapter = TabPagerAdapter(this)

        // Attach TabLayout to ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Pemasukan"
                1 -> "Pengeluaran"
                else -> null
            }
        }.attach()

        // Set the default selected tab to "Pemasukan" (position 0) and ensure background updates
        viewPager.setCurrentItem(0, false)  // Make sure Pemasukan is the starting tab

        // Handle tab selection for custom active/inactive tab backgrounds
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Tab yang aktif
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@NoteTransaction, R.color.Main))

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Tab yang tidak aktif
                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@NoteTransaction, R.color.White))

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Tidak perlu tindakan untuk tab yang dipilih ulang
            }
        })

        // Manually trigger the first tab to be selected and set background immediately
        tabLayout.getTabAt(0)?.let { firstTab ->
            // Update the background and text color manually
            firstTab.view.setBackgroundColor(ContextCompat.getColor(this@NoteTransaction, R.color.Main))

        }
    }
}
