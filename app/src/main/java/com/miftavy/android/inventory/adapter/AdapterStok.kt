package com.miftavy.android.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding
import com.miftavy.android.inventory.databinding.ItemAdapterStokBinding

//class AdapterStok : RecyclerView.Adapter<AdapterStok.ViewHoler>() {
class AdapterStok(private val clickItem: (Stok) -> Unit) : RecyclerView.Adapter<AdapterStok.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class ViewHolder(private val binding: ItemAdapterStokBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(stok: Stok) {
            binding.kodeBarang.text = stok.kode
            binding.NamaBarang.text = stok.barang
            binding.batasMin.text = stok.batasMin
            binding.stok.text = stok.stok

            //inti klik untuk masing-masing baris ke recyclerview nya
            binding.root.setOnClickListener {
                clickItem(stok)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            //memanggil layout itemadapterbarang
            ItemAdapterStokBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listStok.get(position))
    }

    override fun getItemCount(): Int {
        return listStok.size
    }
    //mutablelist adalah array
    private var listStok = mutableListOf<Stok>()

    fun  addItem(list: MutableList<Stok>, clearAll: Boolean = false){
        if (clearAll)
            listStok = mutableListOf()

        listStok.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
}
data class Stok(
    val kode: String? = null,
    val barang: String? = null,
    val batasMin: String? = null,
    val stok: String? = null
)