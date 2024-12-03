package com.example.takasimura.validation




object ValidationUtils {
    fun validateUsername(username: String): String? {
        return when {
            username.isEmpty() -> "Username tidak boleh kosong"
            username.firstOrNull()?.isDigit() == true -> "Username tidak boleh diawali angka"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isEmpty() -> "Password tidak boleh kosong"
            password.length < 8 -> "Password harus minimal 8 karakter"
            else -> null
        }
    }
    fun validateEmail(email: String): String? {
        // Regular expression untuk memvalidasi format email
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return when {
            email.isEmpty() -> "Email tidak boleh kosong"
            !email.matches(emailRegex) -> "Format email tidak valid"
            else -> null
        }
    }
    fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return if (password != confirmPassword) {
            "Password dan Konfirmasi Password tidak sama"
        } else {
            null
        }
    }


}