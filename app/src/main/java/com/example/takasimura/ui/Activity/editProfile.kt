package com.example.takasimura.ui.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.takasimura.R
import com.example.takasimura.viewmodel.EditProfileViewModel
import com.example.takasimura.viewmodel.ProfileViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class editProfile : AppCompatActivity() {

    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private var selectedImageUri: Uri? = null
    private var originalImageUri: Uri? = null // Variabel untuk menyimpan gambar profil asli

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // If a new image is selected, update the selected image URI
            selectedImageUri = uri
            updateProfileImage(uri)
        } else {
            // If no image is selected, show the original image
            originalImageUri?.let {
                updateProfileImage(it)
            } ?: run {
                // If originalImageUri is also null, show a default image or a placeholder
                Toast.makeText(this, "Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        val backIcon = findViewById<ImageView>(R.id.backIcon)
        val profileImageView = findViewById<ImageView>(R.id.profileImage)
        val usernameInput = findViewById<EditText>(R.id.username)
        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.Password)
        val saveButton = findViewById<Button>(R.id.changePasswordButton)

        // Tombol kembali
        backIcon.setOnClickListener {
            onBackPressed()
        }

        // Observasi hasil edit profil
        editProfileViewModel.editProfileResult.observe(this, Observer { responseProfile ->
            if (responseProfile != null) {
                Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke layar sebelumnya
            }
        })

        editProfileViewModel.error.observe(this, Observer { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        // Ambil data profil saat Activity dimulai dan tampilkan gambar profil asli
        profileViewModel.fetchProfile() // Ambil data profil
        profileViewModel.profile.observe(this, Observer { responseProfile ->
            val imageUrl = responseProfile?.profile?.profileImageUrl
            if (!imageUrl.isNullOrEmpty()) {
                originalImageUri = Uri.parse(imageUrl) // Menyimpan URI gambar yang ada
                Glide.with(this)
                    .load(imageUrl)
                    .circleCrop() // Tambahkan efek crop melingkar
                    .into(profileImageView)
            }
        })

        // Klik gambar profil untuk memilih gambar dari galeri
        profileImageView.setOnClickListener {
            openGallery()
        }

        // Tombol simpan untuk mengirim data
        saveButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (validateInputs(username, email, password)) {
                val usernameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), username)
                val emailBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
                val passwordBody = RequestBody.create("text/plain".toMediaTypeOrNull(), password)
                val imagePart = createImagePart(selectedImageUri ?: originalImageUri) // Menggunakan gambar baru atau gambar lama

                if (imagePart != null) {
                    editProfileViewModel.editProfile(usernameBody, emailBody, passwordBody, imagePart)
                } else {
                    Toast.makeText(this, "Silakan pilih gambar profil", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Fungsi untuk membuka galeri
    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    // Fungsi untuk memperbarui gambar profil
    private fun updateProfileImage(uri: Uri) {
        val profileImageView = findViewById<ImageView>(R.id.profileImage)
        Glide.with(this)
            .load(uri)
            .circleCrop()
            .into(profileImageView)
    }

    // Fungsi untuk membuat MultipartBody.Part dari Uri
    private fun createImagePart(uri: Uri?): MultipartBody.Part? {
        if (uri == null) return null

        // Menggunakan ContentResolver untuk mendapatkan file fisik
        val contentResolver = contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val tempFile = File(cacheDir, "temp_image.jpg")

        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }

        val requestBody = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("profileImage", tempFile.name, requestBody)
    }

    // Validasi input dari pengguna
    private fun validateInputs(username: String, email: String, password: String): Boolean {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
