package com.miftavy.android.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding
import com.miftavy.android.inventory.model.DataBarangItem

//class AdapterBarang : RecyclerView.Adapter<AdapterBarang.ViewHolder>(){
class AdapterBarang(private val clickItem: (DataBarangItem?) -> Unit) : RecyclerView.Adapter<AdapterBarang.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class  ViewHolder(private  val binding: ItemAdapterBarangBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bindItem(barang: DataBarangItem?){
                binding.NamaBarang.text = barang?.namaBarang
                binding.harga.text = barang?.hargaBeli.toString()
                binding.jenis.text = barang?.jenisBarang

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
}
data class Barang(
    val barang: String? = null,
    val harga: String? = null,
    val jenis: String? = null
)