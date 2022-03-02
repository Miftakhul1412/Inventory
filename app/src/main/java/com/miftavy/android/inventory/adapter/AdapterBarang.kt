package com.miftavy.android.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.databinding.ItemAdapterBarangBinding
import com.miftavy.android.inventory.model.DataBarangItem

//class AdapterBarang : RecyclerView.Adapter<AdapterBarang.ViewHolder>(){
class AdapterBarang(private val clickItem: (DataBarangItem) -> Unit) : RecyclerView.Adapter<AdapterBarang.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class  ViewHolder(private  val binding: ItemAdapterBarangBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bindItem(barang:DataBarangItem){


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
        holder.bindItem(listbarang.get(position))
    }

    override fun getItemCount(): Int {
        return listbarang.size
    }

    //mutablelist adalah array
    private var listbarang = mutableListOf<DataBarangItem>()

    fun  addItem(list: MutableList<DataBarangItem>, clearAll: Boolean = false){
        if (clearAll)
            listbarang = mutableListOf()

        listbarang.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
}
