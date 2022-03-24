package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding
import com.miftavy.android.inventory.databinding.ItemNewBarangAdapterBinding
import com.miftavy.android.inventory.model.DataBarangItem

class NewBarangAdapter(
private val listBarang: List<DataBarangItem?>?,
private val onListItemClick: (DataBarangItem?) -> Unit
) : RecyclerView.Adapter<NewBarangAdapter.ViewHolder>() {

    inner class ViewHolder(val itemNewBarangAdapterBinding: ItemNewBarangAdapterBinding) :
        RecyclerView.ViewHolder(itemNewBarangAdapterBinding.root) {

        fun onBindItem(dataItem: DataBarangItem?) {
            itemNewBarangAdapterBinding.tanggalMasuk.text = dataItem?.tanggalMasuk
            itemNewBarangAdapterBinding.namaBarang.text = dataItem?.namaBarang
            Glide.with(itemNewBarangAdapterBinding.root.context)
                .load(dataItem?.gambar)
                .into(itemNewBarangAdapterBinding.gambar)

            itemNewBarangAdapterBinding.root.setOnClickListener {
                onListItemClick(dataItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNewBarangAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listBarang?.get(position))
    }

    override fun getItemCount(): Int {
        return listBarang?.size ?: 0
    }
}