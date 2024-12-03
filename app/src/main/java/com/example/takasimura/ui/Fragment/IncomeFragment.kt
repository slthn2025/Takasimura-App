package com.example.takasimura.ui.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.takasimura.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class IncomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_income, container, false)

        // Find views
        val etTanggal = view.findViewById<EditText>(R.id.etTanggal)
        val tvJam = view.findViewById<TextView>(R.id.tvJam)
        val tvKategori = view.findViewById<TextView>(R.id.tvKategori)
        val tvDompet = view.findViewById<TextView>(R.id.tvDompet)
        val calendar = Calendar.getInstance()

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
            popup.menuInflater.inflate(R.menu.menu_item_income, popup.menu)
            try {
                val fields = popup.javaClass.getDeclaredFields()
                for (field in fields) {
                    if (field.name == "mPopup") {
                        field.isAccessible = true
                        val menuPopupHelper = field.get(popup)
                        val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons = classPopupHelper.getMethod("setForceShowIcon", Boolean::class.java)
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Set listener untuk item menu
            popup.setOnMenuItemClickListener { item ->
                tvKategori.text = item.title
                val icon = item.icon
                if (icon != null) {
                    val tintColor = requireContext().getColor(R.color.Main)
                    icon.setTint(tintColor)

                    tvKategori.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
                } else {
                    tvKategori.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }

                true
            }

            popup.show()
        }
        // Handle category dropdown
        tvDompet.setOnClickListener {
            val popup = PopupMenu(requireContext(), tvKategori)
            popup.menuInflater.inflate(R.menu.menu_dompet, popup.menu)

            // Gunakan refleksi untuk mengaktifkan ikon
            try {
                val fields = popup.javaClass.getDeclaredFields()
                for (field in fields) {
                    if (field.name == "mPopup") {
                        field.isAccessible = true
                        val menuPopupHelper = field.get(popup)
                        val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons = classPopupHelper.getMethod("setForceShowIcon", Boolean::class.java)
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Set listener untuk item menu
            popup.setOnMenuItemClickListener { item ->
                tvDompet.text = item.title // Set teks dari item yang dipilih

                // Ambil ikon dari item menu yang dipilih
                val icon = item.icon
                if (icon != null) {
                    val tintColor = requireContext().getColor(R.color.Main)
                    icon.setTint(tintColor)

                    tvDompet.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
                } else {
                    // Jika tidak ada ikon, hapus drawable
                    tvDompet.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }

                true
            }

            popup.show() // Tampilkan PopupMenu
        }
        // Return the view
        return view
    }
}
