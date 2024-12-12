package com.example.takasimura.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseExpanseList
import com.example.takasimura.repository.ExpanseListRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ExpanseListViewModel(application: Application) : AndroidViewModel(application) {

    private val expanseListRepository: ExpanseListRepository = ExpanseListRepository.getInstance(application)

    private val _expanseData = MutableLiveData<List<ResponseExpanseList>?>()
    val ExpanseData: LiveData<List<ResponseExpanseList>?> get() = _expanseData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Fungsi untuk memuat data cash
    fun fetchIncomeData() {
        viewModelScope.launch {
            try {
                val response: Response<List<ResponseExpanseList>> = expanseListRepository.getExpanseList()
                Log.d("Data Income", "Response: $response")
                if (response.isSuccessful) {
                    _expanseData.value = response.body()
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