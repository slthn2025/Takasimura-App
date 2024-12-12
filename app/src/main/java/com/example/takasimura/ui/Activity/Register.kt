package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.takasimura.R
import com.example.takasimura.validation.ValidationUtils
import com.example.takasimura.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Register : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var EmailInput: TextInputEditText
    private lateinit var usernameLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var EmailLayout: TextInputLayout
    private lateinit var progressBar: ProgressBar


    // Initialize RegisterViewModel using ViewModelProvider
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        usernameInput = findViewById(R.id.username)
        passwordInput = findViewById(R.id.password)
        EmailInput = findViewById(R.id.email)
        usernameLayout = findViewById(R.id.usernameLayout)
        passwordLayout = findViewById(R.id.passwordLayout)
        EmailLayout = findViewById(R.id.emailLayout)
        progressBar = findViewById(R.id.loadingProgressBar)

        val cancelButton = findViewById<Button>(R.id.cancelButton)

        // Text watcher to handle validation
        usernameInput.addTextChangedListener(createTextWatcher(usernameLayout) { text ->
            ValidationUtils.validateUsername(text)
        })

        passwordInput.addTextChangedListener(createTextWatcher(passwordLayout) { text ->
            ValidationUtils.validatePassword(text)
        })
        EmailInput.addTextChangedListener(createTextWatcher(EmailLayout) { text ->
            ValidationUtils.validateEmail(text)
        })

        // Observing registration status
        registerViewModel.registrationStatus.observe(this, Observer { status ->
            status?.let {
                // Log status untuk debugging
                println("Registration Status: $it")

                // Jika status berhasil, tampilkan dialog
                if (it.contains("Sukses", ignoreCase = true)) {
                    showSuccessDialog(it) // Dialog akan menangani navigasi
                } else {
                    // Jika gagal, tetap tampilkan pesan kesalahan melalui Toast
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        })


        // Cancel button click listener
        cancelButton.setOnClickListener {
            val intent = Intent(this, OnBoard::class.java)
            startActivity(intent)
            finish()
        }

        // Register button click listener
        findViewById<Button>(R.id.registerButton).setOnClickListener {
            if (validateInputs()) {
                progressBar.visibility = View.VISIBLE
                // Get input values and trigger the ViewModel registration function
                val username = usernameInput.text.toString()
                val email = EmailInput.text.toString()
                val password = passwordInput.text.toString()

                registerViewModel.registerUser(username, email, password)
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Validate Username
        val usernameError = ValidationUtils.validateUsername(usernameInput.text.toString())
        usernameLayout.error = usernameError
        if (usernameError != null) isValid = false

        // Validate Password
        val passwordError = ValidationUtils.validatePassword(passwordInput.text.toString())
        passwordLayout.error = passwordError
        if (passwordError != null) isValid = false

        // Validate Email
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

    private fun showSuccessDialog(message: String) {
        progressBar.visibility = View.GONE
        // Inflate custom layout
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

        // Buat AlertDialog Builder
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(dialogView)

        // Temukan elemen di layout dialog
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val okButton = dialogView.findViewById<Button>(R.id.okButton)

        // Set teks pada elemen dialog
        dialogMessage.text = message

        // Set tombol "OK" untuk melanjutkan
        val dialog = builder.create()
        okButton.setOnClickListener {
            dialog.dismiss() // Tutup dialog

            // Arahkan ke layar berikutnya
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        // Tampilkan dialog
        dialog.show()
    }

}
