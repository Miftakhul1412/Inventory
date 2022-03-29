package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterBarang.Companion.LINEARTYPE
import com.miftavy.android.inventory.adapter.AdapterBarangKeluar
import com.miftavy.android.inventory.databinding.ActivityListBarangKeluarBinding
import com.miftavy.android.inventory.network.Network
import com.miftavy.android.inventory.utils.Constant
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListBarangKeluarActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListBarangKeluarBinding
    private lateinit var adapterBarangKeluar: AdapterBarangKeluar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBarangKeluarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set title
        supportActionBar?.title = "List Barang Keluar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //initialize adapter barang
        adapterBarangKeluar = AdapterBarangKeluar(LINEARTYPE) {
            val showDialogDetailBarangKeluar = DetailBarangKeluarActivity.newInstance(it)
            showDialogDetailBarangKeluar.show(supportFragmentManager, "detail")
        }

        binding.rvListBarangKeluar.apply {
            adapter = adapterBarangKeluar
            layoutManager = LinearLayoutManager(this@ListBarangKeluarActivity)
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
                val response = Network().getService().getListBarangKeluarByUser(Prefs.getString(Constant.EMAIL))
                //update data ke UI
                MainScope().launch {
                    response.dataBarangKeluarByUser?.let { adapterBarangKeluar.addItem(it, true)
                    }
                }
            }catch (e: Throwable){
                e.printStackTrace()
                Log.d("GAGAL", "asd")
                MainScope().launch {
                    Toast.makeText(this@ListBarangKeluarActivity, "uji", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}