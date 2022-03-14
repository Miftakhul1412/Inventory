package com.miftavy.android.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.network.Network

//class AdapterBarang : RecyclerView.Adapter<AdapterBarang.ViewHolder>(){
class AdapterBarang(private val viewTypes: Int = LINEARTYPE, private val clickItem: (DataBarangItem?) -> Unit) : RecyclerView.Adapter<AdapterBarang.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class  ViewHolder(private  val binding: ItemAdapterBarangBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bindItem(barang: DataBarangItem?){
                binding.namaBarang.text = barang?.namaBarang
                Glide.with(binding.root.context)
                    .load("${Network().BASE_URL}${barang?.gambar}")
                    .into(binding.gambar)
                binding.tanggalMasuk.text = barang?.tanggalMasuk



                //inti klik untuk masing-masing baris ke recyclerview nya
                binding.root.setOnClickListener{
                    clickItem(barang)
                }
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            //memanggil layout itemadapterbarang
            ItemAdapterBarangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listBarang?.get(position))
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    //mutablelist adalah array
    private var listBarang = mutableListOf<DataBarangItem?>()

    fun  addItem(list: List<DataBarangItem?>, clearAll: Boolean = false){
        if (clearAll)
            listBarang = mutableListOf()

        listBarang.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
    companion object {
        const val GRIDTYPE = 1
        const val LINEARTYPE = 2
    }
}
