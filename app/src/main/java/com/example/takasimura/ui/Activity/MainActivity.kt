package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.takasimura.R
import com.example.takasimura.ui.Fragment.Calendar
import com.example.takasimura.ui.Fragment.Dompet
import com.example.takasimura.ui.Fragment.Profile
import com.example.takasimura.ui.Fragment.Transaksi
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

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

        // Menampilkan fragment pertama (default)
        if (savedInstanceState == null) {
            replaceFragment(Transaksi())
        }

        // Mengatur item pada BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.transaksi -> {
                    replaceFragment(Transaksi())
                    true
                }
                R.id.kalender -> {
                    replaceFragment(Calendar())
                    true
                }
               R.id.dompet -> {
                    replaceFragment(Dompet())
                    true
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    true
                }
                else -> false
            }
        }
    }

    // Fungsi untuk mengganti fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
