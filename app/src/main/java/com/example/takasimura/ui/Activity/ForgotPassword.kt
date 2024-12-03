package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.takasimura.R
import com.example.takasimura.validation.ValidationUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgotPassword : AppCompatActivity() {
    private lateinit var EmailInput: TextInputEditText
    private lateinit var EmailLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        EmailInput = findViewById(R.id.email)
        EmailLayout = findViewById(R.id.emailLayout)

        EmailInput.addTextChangedListener(createTextWatcher(EmailLayout) { text ->
            ValidationUtils.validateEmail(text)
        })
        val backIcon = findViewById<ImageView>(R.id.backIcon)
        backIcon.setOnClickListener{
            onBackPressed()
        }
        // Tombol submit
        findViewById<Button>(R.id.forgotButton).setOnClickListener {
            if (validateInputs()) {
                val intent = Intent(this, PinPassword::class.java)
                startActivity(intent)
                finish() // Opsional: Menutup SplashScreen agar tidak kembali saat tombol "back" ditekan
            }
        }
    }
    private fun validateInputs(): Boolean {
        var isValid = true

        val emailError = ValidationUtils.validateEmail(EmailInput.text.toString())
        EmailLayout.error = emailError
        if (emailError != null) isValid = false

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