package com.example.takasimura.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.data.DataStoreManager
import com.example.takasimura.model.ResponseLogin
import com.example.takasimura.repository.LoginRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LoginRepository = LoginRepository(application.applicationContext)
    private val dataStoreManager = DataStoreManager.getInstance(application.applicationContext)

    // LiveData untuk status login
    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> get() = _loginStatus

    // LiveData untuk menyimpan data login
    private val _loginResponse = MutableLiveData<ResponseLogin?>()
    val loginResponse: LiveData<ResponseLogin?> get() = _loginResponse

    // Fungsi untuk login
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Panggil fungsi login dari repository
                val response: Response<ResponseLogin> = repository.loginUser(email, password)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginResponse.postValue(it) // Kirim data ke UI
                        _loginStatus.postValue("Login berhasil!")

                        // Simpan token ke DataStore
                        it.token?.let { token ->
                            dataStoreManager.saveToken(token)
                        }
                    } ?: run {
                        _loginStatus.postValue("Login gagal: Data tidak valid")
                    }
                } else {
                    _loginStatus.postValue("Login gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginStatus.postValue("Login gagal: ${e.message}")
            }
        }
    }
}
