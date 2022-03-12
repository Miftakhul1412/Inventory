package com.miftavy.android.inventory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterStok
import com.miftavy.android.inventory.databinding.ActivityListStokBinding
import com.miftavy.android.inventory.model.ResponseListStok
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListStokActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListStokBinding
    private lateinit var adapterStok: AdapterStok
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStokBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize adapter barang
        adapterStok = AdapterStok {

        }
        //set title
        supportActionBar?.title = "List Stok"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvListJenis.apply {
            adapter = adapterStok
            layoutManager = LinearLayoutManager(this@ListStokActivity)
        }

        binding.tambahStok.setOnClickListener {
            Intent(this@ListStokActivity, MainTambahStokActivity::class.java).apply {
                startActivity(this)
            }
        }
        makeRequest()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListStok()
                //update data ke UI
                MainScope().launch {
                    updateUI(response)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: ResponseListStok) {
        response.dataStok?.let {
            adapterStok.addItem(it)
        }
    }
}