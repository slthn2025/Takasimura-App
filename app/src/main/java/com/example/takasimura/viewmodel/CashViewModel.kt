package com.example.takasimura.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseCash
import com.example.takasimura.repository.CashRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CashViewModel(application: Application) : AndroidViewModel(application) {

    private val cashRepository: CashRepository = CashRepository.getInstance(application)

    private val _cashData = MutableLiveData<ResponseCash?>()
    val cashData: LiveData<ResponseCash?> get() = _cashData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Fungsi untuk memuat data cash
    fun fetchCashData() {
        viewModelScope.launch {
            try {
                val response: Response<ResponseCash> = cashRepository.getCashData()
                if (response.isSuccessful) {
                    _cashData.value = response.body()
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
