package com.miftavy.android.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding

//class AdapterBarang : RecyclerView.Adapter<AdapterBarang.ViewHolder>(){
class AdapterBarang(private val clickItem: (Barang) -> Unit) : RecyclerView.Adapter<AdapterBarang.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class  ViewHolder(private  val binding: ItemAdapterBarangBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bindItem(barang: Barang){
                binding.NamaBarang.text = barang.barang
                binding.harga.text = barang.harga
                binding.jenis.text = barang.jenis

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
        holder.bindItem(listBarang.get(position))
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    //mutablelist adalah array
    private var listBarang = mutableListOf<Barang>()

    fun  addItem(list: MutableList<Barang>, clearAll: Boolean = false){
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