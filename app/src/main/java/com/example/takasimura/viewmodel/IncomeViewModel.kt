package com.example.takasimura.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseIncome
import com.example.takasimura.repository.IncomeRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class IncomeViewModel(application: Application) : AndroidViewModel(application) {

    // Menyimpan status pendaftaran
    val registrationStatus: MutableLiveData<String> = MutableLiveData()

    // Menyimpan nilai inputan form pendaftaran
    val date = MutableLiveData<String>()
    val amount = MutableLiveData<Int>()
    val description = MutableLiveData<String>()
    val wallet  = MutableLiveData<String>()
    val category = MutableLiveData<String>()

    // Membuat instance RegisterRepository
    private val repository = IncomeRepository(application.applicationContext)

    // Fungsi untuk mendaftar user
    fun registerUser(date: String, amount:Int, description:String, wallet:String, category:String) {
        // Menjalankan operasi di dalam coroutine untuk melakukan pendaftaran secara asinkron
        viewModelScope.launch {
            try {
                // Panggil registerUser di repository
                val response: Response<ResponseIncome> = repository.income(date, amount, wallet, description, category)

                // Jika response berhasil
                if (response.isSuccessful) {
                    // Dapatkan response body yang berisi message dan token
                    val responseIncome = response.body()
                    val message = responseIncome?.message
                    val id = responseIncome?.id

                    // Menyimpan pesan dan token ke status pendaftaran
                    registrationStatus.value = "Income Berhasil di tambahkan Kedalam Wallet: $message"
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
