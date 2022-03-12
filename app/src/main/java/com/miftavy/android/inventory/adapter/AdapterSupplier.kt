package com.miftavy.android.inventory.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.databinding.ItemAdapterStokBinding
import com.miftavy.android.inventory.databinding.ItemAdapterSupplierBinding
import com.miftavy.android.inventory.model.DataStokItem
import com.miftavy.android.inventory.model.DataSupplierItem

class AdapterSupplier(private val clickItem: (DataSupplierItem?) -> Unit) : RecyclerView.Adapter<AdapterSupplier.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class ViewHolder(private val binding: ItemAdapterSupplierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(supplier: DataSupplierItem?) {
            binding.idSupplier.text = supplier?.idSupplier.toString()
            binding.namaSupplier.text = supplier?.namaSupplier.toString()
            binding.alamat.text = supplier?.alamat.toString()
            binding.kontak.text = supplier?.kontak.toString()
//            binding.namaBarang.text = barang?.namaBarang

            //inti klik untuk masing-masing baris ke recyclerview nya
            binding.root.setOnClickListener {
                clickItem(supplier)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            //memanggil layout itemadapterbarang
            ItemAdapterSupplierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listSupplier?.get(position))
    }

    override fun getItemCount(): Int {
        return listSupplier.size
    }
    //mutablelist adalah array
    private var listSupplier = mutableListOf<DataSupplierItem?>()

    fun  addItem(list: List<DataSupplierItem?>, clearAll: Boolean = false){
        if (clearAll)
            listSupplier = mutableListOf()

        listSupplier.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
}
