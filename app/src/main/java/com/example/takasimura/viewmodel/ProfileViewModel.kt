package com.example.takasimura.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseProfile
import com.example.takasimura.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val profileRepository = ProfileRepository.getInstance(application)

    private val _profile = MutableLiveData<ResponseProfile?>()
    val profile: LiveData<ResponseProfile?> get() = _profile

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Fungsi untuk mengambil profil pengguna
    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val response = profileRepository.getProfile()
                if (response.isSuccessful) {
                    _profile.postValue(response.body())
                } else {
                    _error.postValue("Gagal mengambil profil: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Terjadi kesalahan: ${e.message}")
            }
        }
    }
}
