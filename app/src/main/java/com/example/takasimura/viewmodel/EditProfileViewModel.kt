package com.example.takasimura.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takasimura.model.ResponseProfile
import com.example.takasimura.repository.EditProfileRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val editProfileRepository = EditProfileRepository(application)

    private val _editProfileResult = MutableLiveData<ResponseProfile?>()
    val editProfileResult: LiveData<ResponseProfile?> get() = _editProfileResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Fungsi untuk mengedit profil pengguna
    fun editProfile(
        username: RequestBody,
        email: RequestBody,
        password: RequestBody,
        profileImage: MultipartBody.Part
    ) {
        viewModelScope.launch {
            try {
                val response = editProfileRepository.editProfile(username, email, password, profileImage)
                if (response.isSuccessful) {
                    _editProfileResult.postValue(response.body())
                } else {
                    _error.postValue("Gagal mengedit profil: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Terjadi kesalahan: ${e.message}")
            }
        }
    }
}
