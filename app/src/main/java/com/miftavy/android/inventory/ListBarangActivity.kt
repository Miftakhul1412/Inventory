package com.miftavy.android.inventory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterBarang
import com.miftavy.android.inventory.databinding.ActivityListBarangBinding
import com.miftavy.android.inventory.model.ResponseListBarang
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListBarangActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListBarangBinding
    private lateinit var adapterBarang : AdapterBarang
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize adapter barang
        adapterBarang = AdapterBarang {

        }

        binding.rvListBarang.apply {
            adapter = adapterBarang
            layoutManager = LinearLayoutManager(this@ListBarangActivity)
        }

        makeRequest()
    }

    private fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListBarang()
                //update data ke UI
                MainScope().launch {
                    updateUI(response)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: ResponseListBarang) {
        response.dataBarang?.let {
            adapterBarang.addItem(it)
        }
    }
}