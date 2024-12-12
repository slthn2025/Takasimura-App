package com.example.takasimura.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseRegister
import com.example.takasimura.repository.RegisterRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    // Menyimpan status pendaftaran
    val registrationStatus: MutableLiveData<String> = MutableLiveData()

    // Menyimpan nilai inputan form pendaftaran
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    // Membuat instance RegisterRepository
    private val repository = RegisterRepository(application.applicationContext)

    // Fungsi untuk mendaftar user
    fun registerUser(username: String, email: String, password: String) {
        // Menjalankan operasi di dalam coroutine untuk melakukan pendaftaran secara asinkron
        viewModelScope.launch {
            try {
                // Panggil registerUser di repository
                val response: Response<ResponseRegister> = repository.registerUser(username, email, password)

                // Jika response berhasil
                if (response.isSuccessful) {
                    // Dapatkan response body yang berisi message dan token
                    val responseRegister = response.body()
                    val message = responseRegister?.message
                    val token = responseRegister?.token

                    // Menyimpan pesan dan token ke status pendaftaran
                    registrationStatus.value = "Pendaftaran Sukses: $message"
                } else {
                    // Jika gagal, tangkap pesan dari response atau error lainnya
                    registrationStatus.value = response.message() ?: "Registrasi gagal. Cek input atau koneksi Anda."
                }
            } catch (e: Exception) {
                // Tangani error jika ada kesalahan dalam proses
                registrationStatus.value = "Error: ${e.message}"
            }
        }
    }

}
