package com.miftavy.android.inventory.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.databinding.ItemAdapterSupplierBinding
import com.miftavy.android.inventory.databinding.ItemAdapterUserBinding
import com.miftavy.android.inventory.model.DataSupplierItem
import com.miftavy.android.inventory.model.DataUserItem

class AdapterUser(private val clickItem: (DataUserItem?) -> Unit) : RecyclerView.Adapter<AdapterUser.ViewHolder>() {

    //memanggil class utama yaitu class adapterbarang

    inner class ViewHolder(private val binding: ItemAdapterUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(user: DataUserItem?) {
            binding.id.text = user?.id.toString()
            binding.name.text = user?.name.toString()
            binding.email.text = user?.email.toString()
            binding.jabatan.text = user?.jabatan.toString()
            binding.level.text = user?.level.toString()
//            binding.namaBarang.text = barang?.namaBarang

            //inti klik untuk masing-masing baris ke recyclerview nya
            binding.root.setOnClickListener {
                clickItem(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            //memanggil layout itemadapterbarang
            ItemAdapterUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listUser?.get(position))
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
    //mutablelist adalah array
    private var listUser = mutableListOf<DataUserItem?>()

    fun  addItem(list: List<DataUserItem?>, clearAll: Boolean = false){
        if (clearAll)
            listUser = mutableListOf()

        listUser.addAll(list)

        //notifyItemInserted(listBarang.size)
        notifyDataSetChanged()
    }
}
