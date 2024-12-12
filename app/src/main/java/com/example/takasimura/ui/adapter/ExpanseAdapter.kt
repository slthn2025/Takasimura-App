
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takasimura.R
import com.example.takasimura.model.ResponseExpanseList

class ExpanseAdapter : RecyclerView.Adapter<ExpanseAdapter.ViewHolder>() {

    private var data: List<ResponseExpanseList>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaksi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = data?.get(position)
        holder.tvTanggal.text = transaksi?.date
        holder.tvJumlah.text = transaksi?.amount.toString()
        holder.tvKategori.text = transaksi?.category
        holder.tvDeskripsi.text = transaksi?.description
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun updateData(data: List<ResponseExpanseList>?) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvJumlah: TextView = itemView.findViewById(R.id.tvJumlah)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
    }
}