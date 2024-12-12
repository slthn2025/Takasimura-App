package com.example.takasimura.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseIncomeList
import com.example.takasimura.repository.IncomeListRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class IncomeListViewModel(application: Application) : AndroidViewModel(application) {

    private val incomeListRepository: IncomeListRepository = IncomeListRepository.getInstance(application)

    private val _incomeData = MutableLiveData<List<ResponseIncomeList>?>()
    val incomeData: LiveData<List<ResponseIncomeList>?> get() = _incomeData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Fungsi untuk memuat data cash
    fun fetchIncomeData() {
        viewModelScope.launch {
            try {
                val response: Response<List<ResponseIncomeList>> = incomeListRepository.getIncomeList()
                Log.d("Data Income", "Response: $response")
                if (response.isSuccessful) {
                    _incomeData.value = response.body()
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