package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.takasimura.R
import com.example.takasimura.data.DataStoreManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Inisialisasi DataStoreManager menggunakan singleton
        dataStoreManager = DataStoreManager.getInstance(applicationContext)

        // Inisialisasi tombol dan card view
        val cardView: androidx.cardview.widget.CardView = findViewById(R.id.cardButton)
        val button: Button = findViewById(R.id.customButton)
        button.backgroundTintList = null

        // Periksa apakah token ada
        lifecycleScope.launch {
            dataStoreManager.getToken().collect { token ->
                if (token != null) {
                    button.visibility = View.GONE
                    cardView.visibility = View.GONE
                    navigateToMain()
                } else {
                    // Jika token tidak ada, tampilkan tombol dan navigasi ke OnBoardActivity
                    button.setOnClickListener {
                        button.visibility = View.VISIBLE
                        cardView.visibility = View.VISIBLE
                        val intent = Intent(this@SplashScreen, OnBoard::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Menutup SplashScreen agar tidak bisa kembali
    }
}
