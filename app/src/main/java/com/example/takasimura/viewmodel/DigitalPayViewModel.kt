package com.example.takasimura.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseCash
import com.example.takasimura.model.ResponseDigitalPayement
import com.example.takasimura.repository.CashRepository
import com.example.takasimura.repository.DigitalPayRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class DigitalPayViewModel(application: Application) : AndroidViewModel(application) {

    private val digitalPayRepository: DigitalPayRepository = DigitalPayRepository.getInstance(application)

    private val _DigitalPay = MutableLiveData<ResponseDigitalPayement?>()
    val digitalPay: LiveData<ResponseDigitalPayement?> get() = _DigitalPay

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Fungsi untuk memuat data cash
    fun fetchDigitalPay() {
        viewModelScope.launch {
            try {
                val response: Response<ResponseDigitalPayement> = digitalPayRepository.getDigitalPay()
                if (response.isSuccessful) {
                    _DigitalPay.value = response.body()
                } else {
                    _error.value = "Failed to fetch cash data: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "An error occurred: ${e.localizedMessage}"
            }
        }
    }
}
