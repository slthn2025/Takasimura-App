package com.example.takasimura.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takasimura.R
import com.example.takasimura.viewmodel.CashViewModel
import com.example.takasimura.viewmodel.DigitalPayViewModel

class Dompet : Fragment() {

    private lateinit var cashViewModel: CashViewModel
    private lateinit var digitalPayViewModel: DigitalPayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dompet, container, false)

        // Inisialisasi CashViewModel tanpa ViewModelFactory
        cashViewModel = ViewModelProvider(this).get(CashViewModel::class.java)
        digitalPayViewModel= ViewModelProvider(this).get(DigitalPayViewModel::class.java)

        // Referensi ke TextView
        val cashAmountTextView: TextView = rootView.findViewById(R.id.cashAmount)
        val digitalPayTextView: TextView = rootView.findViewById(R.id.digitalPaymentAmount)

        // Observasi LiveData cashData
        cashViewModel.cashData.observe(viewLifecycleOwner) { cash ->
            if (cash != null) {
                // Update teks dengan jumlah cash
                cashAmountTextView.text = getString(R.string.rp_format, cash.totalCashWallet)
            }
        }

        // Observasi LiveData error
        cashViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
        //digital pay
        digitalPayViewModel.digitalPay.observe(viewLifecycleOwner) { digitalCash ->
            if (digitalCash != null) {
                // Update teks dengan jumlah cash
                digitalPayTextView.text = getString(R.string.rp_format, digitalCash.totalDigitalPaymentWallet)
            }
        }

        // Observasi LiveData error
        digitalPayViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }




        // Fetch data cash
        cashViewModel.fetchCashData()
        digitalPayViewModel.fetchDigitalPay()

        return rootView
    }
}
