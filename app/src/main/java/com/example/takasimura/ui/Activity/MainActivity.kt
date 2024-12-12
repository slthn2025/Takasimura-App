package com.example.takasimura.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.takasimura.R
import com.example.takasimura.ui.Fragment.Calendar
import com.example.takasimura.ui.Fragment.Dompet
import com.example.takasimura.ui.Fragment.ExpanseList
import com.example.takasimura.ui.Fragment.Profile
import com.example.takasimura.ui.Fragment.Transaksi
import com.example.takasimura.viewmodel.CashViewModel
import com.example.takasimura.viewmodel.DigitalPayViewModel
import com.example.takasimura.viewmodel.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Inisialisasi ViewModel menggunakan delegasi viewModels
    private val profileViewModel: ProfileViewModel by viewModels()
    private val cashViewModel: CashViewModel by viewModels()
    private val digitalPayViewModel: DigitalPayViewModel by viewModels()

    private var cashBalance: Int = 0
    private var digitalPayBalance: Int = 0

    private lateinit var balanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize the TextView for Balance
        balanceTextView = findViewById(R.id.Balance)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val iconScan = findViewById<ImageView>(R.id.iconScan)
        val iconSearch = findViewById<ImageView>(R.id.iconSearch)
        val addTransaction = findViewById<ImageView>(R.id.addTransaction)
        val userNameTextView: TextView = findViewById(R.id.userName)
        val imgProfile = findViewById<ImageView>(R.id.imgProfile)
        val Wallet = findViewById<ImageView>(R.id.iconWallet)

        userNameTextView.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, Profile())
            fragmentTransaction.commit()
        }

        imgProfile.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, Profile())
            fragmentTransaction.commit()
        }

        Wallet.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, Dompet())
            fragmentTransaction.commit()
        }

        addTransaction.setOnClickListener {
            startActivity(Intent(this, NoteTransaction::class.java))
        }

        iconScan.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        iconSearch.setOnClickListener {
            startActivity(Intent(this, Search::class.java))
        }

        if (savedInstanceState == null) {
            replaceFragment(Transaksi())
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.transaksi_income -> {
                    replaceFragment(Transaksi())
                    true
                }
                R.id.kalender -> {
                    replaceFragment(Calendar())
                    true
                }
                R.id.transaksi_expanse -> {
                    replaceFragment(ExpanseList())
                    true
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    true
                }
                else -> false
            }
        }

        // Observasi data dari ProfileViewModel
        profileViewModel.profile.observe(this) { profileResponse ->
            profileResponse?.profile?.let { profile ->
                val username = profile.username ?: "Unknown"
                val profileImageUrl = profile.profileImageUrl

                userNameTextView.text = username

                if (!profileImageUrl.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(profileImageUrl)
                        .placeholder(R.drawable.ic_down)
                        .error(R.drawable.ic_down)
                        .circleCrop()
                        .into(imgProfile)
                } else {
                    imgProfile.setImageResource(R.drawable.ic_down)
                }
            }
        }

        profileViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        profileViewModel.fetchProfile()

        // Cash View
        cashViewModel.cashData.observe(this) { cashResponse ->
            cashResponse?.let { response ->
                cashBalance = (response.totalCashWallet ?: 0).toInt()
                updateBalance()  // Update balance after cash data is fetched
            }
        }

        cashViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        // Memulai pengambilan data cash
        cashViewModel.fetchCashData()

        // DigitalPay
        digitalPayViewModel.digitalPay.observe(this) { digitalPayResponse ->
            digitalPayResponse?.let { response ->
                digitalPayBalance = (response.totalDigitalPaymentWallet ?: 0).toInt()
                updateBalance()  // Update balance after digital pay data is fetched
            }
        }

        digitalPayViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        // Memulai pengambilan data digital pay
        digitalPayViewModel.fetchDigitalPay()
    }

    private fun updateBalance() {
        val totalBalance = cashBalance + digitalPayBalance
        balanceTextView.text = "Rp.$totalBalance"
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
