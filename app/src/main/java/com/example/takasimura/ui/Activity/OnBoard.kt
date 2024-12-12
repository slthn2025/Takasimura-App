package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.takasimura.R

class OnBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_board)

        val buttonLogin: Button = findViewById(R.id.onLoginButton)
        buttonLogin.backgroundTintList = null

        val buttonRegister: Button = findViewById(R.id.onRegisterButton)
        buttonRegister.backgroundTintList = null

        buttonLogin.setOnClickListener {
            // Navigasi ke OnBoardActivity
            val intent = Intent(this, com.example.takasimura.ui.Activity.Login::class.java)
            startActivity(intent)
            finish() // Opsional: Menutup SplashScreen agar tidak kembali saat tombol "back" ditekan
        }

        buttonRegister.setOnClickListener {
            // Navigasi ke OnBoardActivity
            val intent = Intent(this, com.example.takasimura.ui.Activity.Register::class.java)
            startActivity(intent)
            finish() // Opsional: Menutup SplashScreen agar tidak kembali saat tombol "back" ditekan
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()  // Menutup aplikasi dengan keluar dari semua aktivitas yang ada di belakangnya
    }
}