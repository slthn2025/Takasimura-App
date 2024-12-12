package com.example.takasimura.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.takasimura.R
import com.example.takasimura.data.DataStoreManager
import com.example.takasimura.ui.Activity.OnBoard
import com.example.takasimura.ui.Activity.Settings
import com.example.takasimura.ui.Activity.editProfile
import com.example.takasimura.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Profile : Fragment() {

    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var profileViewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Inisialisasi DataStoreManager
        dataStoreManager = DataStoreManager.getInstance(requireContext())

        // Inisialisasi ProfileViewModel tanpa Factory
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Ambil data profil
        profileViewModel.fetchProfile()

        // Observasi perubahan data profil
        profileViewModel.profile.observe(viewLifecycleOwner, Observer { responseProfile ->
            if (responseProfile?.profile != null) {
                val usernameTextView: TextView = rootView.findViewById(R.id.usernameText)
                usernameTextView.text = responseProfile.profile.username

                val profileImageView: ImageView = rootView.findViewById(R.id.profileImage)
                Glide.with(this)
                    .load(responseProfile.profile.profileImageUrl)
                    .circleCrop()

                    .into(profileImageView)
            }
        })

        // Observasi error jika ada
        profileViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })

        // Menangani klik pada Edit Profile
        val editProfileLayout: LinearLayout = rootView.findViewById(R.id.editProfile)
        editProfileLayout.setOnClickListener {
            navigateToEditProfile()
        }

        // Menangani klik pada Logout
        val logoutLayout: LinearLayout = rootView.findViewById(R.id.logout)
        logoutLayout.setOnClickListener {
            logout()
        }

        return rootView
    }

    private fun navigateToEditProfile() {
        val intent = Intent(activity, editProfile::class.java)
        startActivity(intent)
    }

    private fun navigateToSettings() {
        val intent = Intent(activity, Settings::class.java)
        startActivity(intent)
    }

    private fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.clearToken()

            activity?.runOnUiThread {
                val intent = Intent(activity, OnBoard::class.java)
                startActivity(intent)
            }
        }
    }
}
