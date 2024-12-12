package com.example.takasimura.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager private constructor(private val context: Context) {

    // Deklarasikan DataStore menggunakan extension property
    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    // Kunci untuk menyimpan token
    private val TOKEN_KEY = stringPreferencesKey("token")

    // Simpan token ke DataStore
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Ambil token dari DataStore
    fun getToken(): Flow<String?> {
        return context.dataStore.data
            .map { preferences ->
                preferences[TOKEN_KEY] // null jika token tidak ada
            }
    }

    // Menghapus token dari DataStore
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY) // Menghapus token
        }
    }

    // Singleton pattern untuk memastikan hanya ada satu instance DataStoreManager
    companion object {
        @Volatile
        private var INSTANCE: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataStoreManager(context).also { INSTANCE = it }
            }
        }
    }
}
