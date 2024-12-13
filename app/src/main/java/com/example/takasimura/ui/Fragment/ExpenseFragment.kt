package com.example.takasimura.ui.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takasimura.R
import com.example.takasimura.ui.Activity.MainActivity
import com.example.takasimura.viewmodel.ExpanseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExpenseFragment : Fragment() {

    private lateinit var expanseViewModel: ExpanseViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_expense, container, false)

        expanseViewModel = ViewModelProvider(this).get(ExpanseViewModel::class.java)

        // Find views
        val etTanggal = view.findViewById<EditText>(R.id.etTanggal)
        val tvJam = view.findViewById<TextView>(R.id.tvJam)
        val tvKategori = view.findViewById<TextView>(R.id.tvKategori)
        val tvDompet = view.findViewById<TextView>(R.id.tvDompet)
        val etDeskripsi = view.findViewById<EditText>(R.id.etDeskripsi)
        val etJumlah = view.findViewById<EditText>(R.id.etJumlah)
        val btnSimpan = view.findViewById<Button>(R.id.btnSimpan)
        val progressBar = view.findViewById<ProgressBar>(R.id.loadingProgressBar)
        val calendar = Calendar.getInstance()

        // Observe registrationStatus to display feedback to the user
        expanseViewModel.registrationStatus.observe(viewLifecycleOwner) { status ->
            progressBar.visibility = View.GONE
            showSuccessDialog()
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        }

        // Handle date and time picker
        etTanggal.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    etTanggal.setText(dateFormat.format(calendar.time))

                    // Show TimePickerDialog after selecting a date
                    TimePickerDialog(
                        requireContext(),
                        { _, hour, minute ->
                            calendar.set(Calendar.HOUR_OF_DAY, hour)
                            calendar.set(Calendar.MINUTE, minute)
                            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            tvJam.text = timeFormat.format(calendar.time)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Handle category dropdown
        tvKategori.setOnClickListener {
            val popup = PopupMenu(requireContext(), tvKategori)
            popup.menuInflater.inflate(R.menu.menu_kategori, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                tvKategori.text = item.title
                true
            }
            popup.show()
        }

        // Handle wallet dropdown
        tvDompet.setOnClickListener {
            val popup = PopupMenu(requireContext(), tvDompet)
            popup.menuInflater.inflate(R.menu.menu_dompet, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                tvDompet.text = item.title
                true
            }
            popup.show()
        }

        // Handle save button click
        btnSimpan.setOnClickListener {
            val date = etTanggal.text.toString()
            val time = tvJam.text.toString()
            val amount = etJumlah.text.toString().toIntOrNull()
            val description = etDeskripsi.text.toString()
            val wallet = tvDompet.text.toString()
            val category = tvKategori.text.toString()
            progressBar.visibility = View.VISIBLE

            if (date.isNotEmpty() && time.isNotEmpty() && amount != null && description.isNotEmpty() && wallet.isNotEmpty() && category.isNotEmpty()) {
                expanseViewModel.expanse(
                    date = "$date $time",
                    amount = amount,
                    description = description,
                    wallet = wallet,
                    category = category
                )
            } else {
                Toast.makeText(requireContext(), "Mohon lengkapi semua inputan.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun showSuccessDialog() {
        // Inflate custom layout
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

        // Buat AlertDialog Builder
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        // Temukan elemen di layout dialog
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val okButton = dialogView.findViewById<Button>(R.id.okButton)

        // Set teks pada elemen dialog
        dialogMessage.text = "Pengeluaran berhasil disimpan."

        // Set tombol "OK" untuk melanjutkan
        val dialog = builder.create()
        okButton.setOnClickListener {
            dialog.dismiss() // Tutup dialog

            // Opsional: Navigasi ke aktivitas lain jika diperlukan
             val intent = Intent(requireContext(), MainActivity::class.java)
             startActivity(intent)
             requireActivity().finish()
        }

        // Tampilkan dialog
        dialog.show()
    }

}
