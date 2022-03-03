package com.miftavy.android.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding
import com.miftavy.android.inventory.databinding.ItemAdapterStokBinding
import com.miftavy.android.inventory.model.DataStokItem

//class AdapterStok : RecyclerView.Adapter<AdapterStok.ViewHoler>() {
class AdapterStok(private val clickItem: (DataStokItem?) -> Unit) : RecyclerView.Adapter<AdapterStok.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class ViewHolder(private val binding: ItemAdapterStokBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(stok: DataStokItem?) {
            binding.kodeStok.text = stok?.kodeBarang
            binding.stok.text = stok?.stok.toString()
//            binding.namaBarang.text = barang?.namaBarang

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
        holder.bindItem(listStok?.get(position))
    }

    override fun getItemCount(): Int {
        return listStok.size
    }
    //mutablelist adalah array
    private var listStok = mutableListOf<DataStokItem?>()

    fun  addItem(list: MutableList<DataStokItem?>, clearAll: Boolean = false){
        if (clearAll)
            listStok = mutableListOf()

        listStok.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
}
