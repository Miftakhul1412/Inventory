package com.miftavy.android.inventory

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
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

        //set title
        supportActionBar?.title = "List Barang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //initialize adapter barang
        adapterBarang = AdapterBarang(AdapterBarang.LINEARTYPE) {
            val showDialogDetailBarang = DetailBarangActivity.newInstance(it)
            showDialogDetailBarang.show(supportFragmentManager, "detail")
        }

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false

            makeRequest()
        }

        binding.rvListBarang.apply {
            adapter = adapterBarang
            layoutManager = LinearLayoutManager(this@ListBarangActivity)
        }

        makeRequest()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListBarang()
                //update data ke UI
                MainScope().launch {
                    response.dataBarang?.let { adapterBarang.addItem(it, true)
                    }
                }
            }catch (e: Throwable){
                e.printStackTrace()
                Log.d("GAGAL", "asd")
                MainScope().launch {
                    Toast.makeText(this@ListBarangActivity, "uji", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}