package com.miftavy.android.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterStok
import com.miftavy.android.inventory.adapter.AdapterSupplier
import com.miftavy.android.inventory.databinding.ActivityListStokBinding
import com.miftavy.android.inventory.databinding.ActivityListSupplierBinding
import com.miftavy.android.inventory.model.ResponseListStok
import com.miftavy.android.inventory.model.ResponseListSupplier
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListSupplierActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListSupplierBinding
    private lateinit var adapterSupplier: AdapterSupplier
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListSupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize adapter barang
        adapterSupplier = AdapterSupplier {

        }
        //set title
        supportActionBar?.title = "List Supplier"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvListSupplier.apply {
            adapter = adapterSupplier
            layoutManager = LinearLayoutManager(this@ListSupplierActivity)
        }

//        binding.tambahSupplier.setOnClickListener {
//            Intent(this@ListSupplierActivity, MainTambahSupplierActivity::class.java).apply {
//                startActivity(this)
//            }
//        }
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
                val response = Network().getService().getListSupplier()
                //update data ke UI
                MainScope().launch {
                    updateUI(response)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: ResponseListSupplier) {
        response.dataSupplier?.let {
            adapterSupplier.addItem(it)
        }
    }

}