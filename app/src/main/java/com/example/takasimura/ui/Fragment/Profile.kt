package com.example.takasimura.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.takasimura.R
import com.example.takasimura.ui.Activity.Settings
import com.example.takasimura.ui.Activity.editProfile

class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Menangani klik pada Edit Profile
        val editProfileLayout: LinearLayout = rootView.findViewById(R.id.editProfile
        )
        editProfileLayout.setOnClickListener {
            // Navigasi ke EditProfileFragment atau Activity
            navigateToEditProfile()
        }

        // Menangani klik pada Settings
        val settingsLayout: LinearLayout = rootView.findViewById(R.id.settings)
        settingsLayout.setOnClickListener {
            // Navigasi ke SettingsFragment atau Activity
            navigateToSettings()
        }

//        // Menangani klik pada Logout
//        val logoutLayout: LinearLayout = rootView.findViewById(R.id.logout)
//        logoutLayout.setOnClickListener {
//            // Logout logika
//            logout()
//        }

        return rootView
    }

    private fun navigateToEditProfile() {
        // Misalnya kita ingin navigasi ke Activity, Anda bisa menggunakan Intents seperti berikut:
        val intent = Intent(activity, editProfile::class.java)
        startActivity(intent)
    }

    private fun navigateToSettings() {
        // Misalnya kita ingin navigasi ke Settings Activity
        val intent = Intent(activity, Settings::class.java)
        startActivity(intent)
    }

//    private fun logout() {
//        // Logika untuk logout: hapus sesi, token, dll.
//        val intent = Intent(activity, LoginActivity::class.java)
//        startActivity(intent)
//        activity?.finish()  // Tutup activity saat ini setelah logout
//    }
}
