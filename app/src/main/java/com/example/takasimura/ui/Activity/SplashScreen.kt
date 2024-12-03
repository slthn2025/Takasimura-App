package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.takasimura.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Inisialisasi tombol
        val button: Button = findViewById(R.id.customButton)
        button.backgroundTintList = null

        // Tambahkan listener untuk tombol
        button.setOnClickListener {
            // Navigasi ke OnBoardActivity
            val intent = Intent(this, com.example.takasimura.ui.Activity.OnBoard::class.java)
            startActivity(intent)
            finish() // Opsional: Menutup SplashScreen agar tidak kembali saat tombol "back" ditekan
        }
    }
}