package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterJenis
import com.miftavy.android.inventory.databinding.ActivityListJenisBinding
import com.miftavy.android.inventory.model.ResponseListJenis
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListJenisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListJenisBinding
    private lateinit var adapterJenis : AdapterJenis
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListJenisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize adapter barang
        adapterJenis = AdapterJenis {

        }

        binding.rvListJenis.apply {
            adapter = adapterJenis
            layoutManager = LinearLayoutManager(this@ListJenisActivity)
        }

        makeRequest()
    }

    private fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListJenis()
                //update data ke UI
                MainScope().launch {
                    updateUI(response)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: ResponseListJenis) {
        response.dataJenis?.let {
            adapterJenis.addItem(it)
        }
    }
}