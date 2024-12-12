package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.takasimura.R
import com.example.takasimura.validation.ValidationUtils
import com.example.takasimura.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var usernameLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameInput = findViewById(R.id.username)
        passwordInput = findViewById(R.id.password)
        usernameLayout = findViewById(R.id.usernameLayout)
        passwordLayout = findViewById(R.id.passwordLayout)
        progressBar = findViewById(R.id.loadingProgressBar)

        // Inisialisasi ViewModel dengan context
        loginViewModel = LoginViewModel(application)

        // Observasi LiveData loginStatus
        loginViewModel.loginStatus.observe(this, Observer { status ->
            status?.let {
                // Tampilkan status login
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                // Jika login berhasil, arahkan ke MainActivity
                if (it.contains("berhasil", ignoreCase = true)) {
                    navigateToHome()
                }
            }
        })

        val loginButton = findViewById<Button>(R.id.LoginButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Tombol Login
        loginButton.setOnClickListener {
            if (validateInputs()) {
                progressBar.visibility = View.VISIBLE
                val email = usernameInput.text.toString()
                val password = passwordInput.text.toString()
                // Panggil ViewModel untuk melakukan login
                loginViewModel.loginUser(email, password)
            }
        }

        // Tombol Cancel
        cancelButton.setOnClickListener {
            val intent = Intent(this, OnBoard::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Validasi Username
        val usernameError = ValidationUtils.validateUsername(usernameInput.text.toString())
        usernameLayout.error = usernameError
        if (usernameError != null) isValid = false

        // Validasi Password
        val passwordError = ValidationUtils.validatePassword(passwordInput.text.toString())
        passwordLayout.error = passwordError
        if (passwordError != null) isValid = false

        return isValid
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        progressBar.visibility = View.GONE
        finish()
    }
}
