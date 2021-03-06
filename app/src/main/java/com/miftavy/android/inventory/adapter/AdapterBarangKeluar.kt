package com.miftavy.android.inventory.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.adapter.AdapterBarang.Companion.LINEARTYPE
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding
import com.miftavy.android.inventory.databinding.ItemAdapterBarangKeluarBinding
import com.miftavy.android.inventory.databinding.ItemAdapterSupplierBinding
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.model.DataBarangKeluarByUserItem
import com.miftavy.android.inventory.model.DataBarangKeluarItem
import com.miftavy.android.inventory.model.DataSupplierItem
import com.miftavy.android.inventory.network.Network

class AdapterBarangKeluar(private val viewTypes: Int = AdapterBarangKeluar.LINEARTYPE, private val clickItem: (DataBarangKeluarByUserItem?) -> Unit) : RecyclerView.Adapter<AdapterBarangKeluar.ViewHolder>() {


    //memanggil class utama yaitu class adapterbarang

    inner class  ViewHolder(private  val binding: ItemAdapterBarangKeluarBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bindItem(barangkeluar: DataBarangKeluarByUserItem?){
            //binding.kodeBarangKeluar.text = barangkeluar?.kodeBarangKeluar.toString()
            binding.namaBarang.text=barangkeluar?.namaBarang
            binding.statusPinjam.text=barangkeluar?.statusPinjam
            binding.jenisBarang.text=barangkeluar?.jenisBarang
            Glide.with(binding.root.context)
                .load("${Network().BASE_URL}${barangkeluar?.gambar}")
                .into(binding.gambar)
            binding.tanggalkeluar.text=barangkeluar?.tanggalKeluar
//            //inti klik untuk masing-masing baris ke recyclerview nya
//            binding.root.setOnClickListener{
//                clickItem(barangkeluar)
//            }
        }

//        private fun clickItem(barangkeluar: DataBarangKeluarItem?) {
//
//        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            //memanggil layout itemadapterbarang
            ItemAdapterBarangKeluarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listBarangkeluar?.get(position))
    }

    override fun getItemCount(): Int {
        return listBarangkeluar.size
    }

    //mutablelist adalah array
    private var listBarangkeluar = mutableListOf<DataBarangKeluarByUserItem?>()

    fun  addItem(list: List<DataBarangKeluarByUserItem?>, clearAll: Boolean = false){
        if (clearAll)
            listBarangkeluar = mutableListOf()

        listBarangkeluar.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
    companion object {
        const val GRIDTYPE = 1
        const val LINEARTYPE = 2
    }
}
