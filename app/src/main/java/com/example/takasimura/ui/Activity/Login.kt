package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.takasimura.R
import com.example.takasimura.validation.ValidationUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var usernameLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        usernameInput = findViewById(R.id.username)
        passwordInput = findViewById(R.id.password)
        usernameLayout = findViewById(R.id.usernameLayout)
        passwordLayout = findViewById(R.id.passwordLayout)

        val cancelButton = findViewById<Button>(R.id.cancelButton)



        usernameInput.addTextChangedListener(createTextWatcher(usernameLayout) { text ->
            ValidationUtils.validateUsername(text)
        })

        passwordInput.addTextChangedListener(createTextWatcher(passwordLayout) { text ->
            ValidationUtils.validatePassword(text)
        })

        cancelButton.setOnClickListener{
            val intent = Intent(this, OnBoard::class.java)
            startActivity(intent)
            finish()
        }


        // Tombol submit
        findViewById<Button>(R.id.LoginButton).setOnClickListener {
            if (validateInputs()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val forgotPasswordTextView: TextView = findViewById(R.id.forgotPassword)
        forgotPasswordTextView.setOnClickListener {
            // Navigasi ke Forgot Password Activity
            val intent = Intent(this, com.example.takasimura.ui.Activity.ForgotPassword::class.java)
            startActivity(intent)
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

    private fun createTextWatcher(
        textInputLayout: TextInputLayout,
        validation: (String) -> String?
    ): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val error = validation(s.toString())
                textInputLayout.error = error
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

}