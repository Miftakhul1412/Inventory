package com.miftavy.android.inventory.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.databinding.ItemAdapterJenisBinding
import com.miftavy.android.inventory.databinding.ItemAdapterStokBinding
import com.miftavy.android.inventory.model.DataJenisItem

class AdapterJenis(private val clickItem: (DataJenisItem?) -> Unit) : RecyclerView.Adapter<AdapterJenis.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class ViewHolder(private val binding: ItemAdapterJenisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(jenis: DataJenisItem?) {
            binding.idJenisBarang.text = jenis?.idJenisBarang.toString()
            binding.jenisBarang.text = jenis?.jenisBarang

            //inti klik untuk masing-masing baris ke recyclerview nya
            binding.root.setOnClickListener {
                clickItem(jenis)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            //memanggil layout itemadapterbarang
            ItemAdapterJenisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listJenis?.get(position))
    }

    override fun getItemCount(): Int {
        return listJenis.size
    }
    //mutablelist adalah array
    private var listJenis = mutableListOf<DataJenisItem?>()

    fun  addItem(list: MutableList<DataJenisItem?>, clearAll: Boolean = false){
        if (clearAll)
            listJenis = mutableListOf()

        listJenis.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
}