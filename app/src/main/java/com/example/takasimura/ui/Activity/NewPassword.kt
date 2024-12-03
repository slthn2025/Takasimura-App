package com.example.takasimura.ui.Activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.takasimura.R
import com.example.takasimura.validation.ValidationUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NewPassword : AppCompatActivity() {

    private lateinit var newPassword: TextInputEditText
    private lateinit var newPasswordLayout: TextInputLayout
    private lateinit var confirmPassword: TextInputEditText
    private lateinit var confirmPasswordLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_password)

        newPassword = findViewById(R.id.newPassword)
        newPasswordLayout = findViewById(R.id.newPasswordLayout)
        confirmPassword = findViewById(R.id.confirmPassword)
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout)

        // Tambahkan TextWatcher untuk validasi saat mengetik
        newPassword.addTextChangedListener(createPasswordTextWatcher())
        confirmPassword.addTextChangedListener(createConfirmPasswordTextWatcher())

        // Validasi ketika tombol submit ditekan
        findViewById<Button>(R.id.changePasswordButton).setOnClickListener {
            if (validatePasswords()) {
                Toast.makeText(this, "Password berhasil diatur ulang", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // TextWatcher untuk password baru
    private fun createPasswordTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newPasswordError = ValidationUtils.validatePassword(s.toString())
                newPasswordLayout.error = newPasswordError
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    // TextWatcher untuk konfirmasi password
    private fun createConfirmPasswordTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val confirmPasswordError = ValidationUtils.validateConfirmPassword(
                    newPassword.text.toString(),
                    s.toString()
                )
                confirmPasswordLayout.error = confirmPasswordError
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    // Validasi akhir saat tombol ditekan
    private fun validatePasswords(): Boolean {
        var isValid = true

        val newPasswordError = ValidationUtils.validatePassword(newPassword.text.toString())
        newPasswordLayout.error = newPasswordError
        if (newPasswordError != null) isValid = false

        val confirmPasswordError = ValidationUtils.validateConfirmPassword(
            newPassword.text.toString(),
            confirmPassword.text.toString()
        )
        confirmPasswordLayout.error = confirmPasswordError
        if (confirmPasswordError != null) isValid = false

        return isValid
    }
}
