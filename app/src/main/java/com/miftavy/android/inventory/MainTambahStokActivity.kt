package com.miftavy.android.inventory

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterStok
import com.miftavy.android.inventory.databinding.ActivityMainTambahStokBinding
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import kotlin.concurrent.schedule


class MainTambahStokActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainTambahStokBinding
    private  lateinit var adapterStok: AdapterStok
    private var listBarang = mutableListOf<DataBarangItem?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahStokBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getListBarang()

        //adapterBarang = AdapterBarang()
        adapterStok = AdapterStok{
            Toast.makeText(this, it?.kodeBarang, Toast.LENGTH_SHORT).show()
        }
        binding.rvStok.apply {
            adapter = adapterStok
            layoutManager = LinearLayoutManager(this@MainTambahStokActivity)
        }

// kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener{
            //ambil inputan text edit text
            if (binding.kodeStok.text.toString()
                .isEmpty() || binding.kodeBarang.selectedItem.toString().isNotEmpty()

        ) {
            Toast.makeText(this, "Isi dan pilih semua terlebih dahulu", Toast.LENGTH_SHORT).show()
            //showMessage("Isi dan pilih semua terlebih dahulu")
        } else {
//                showLoading()
            val kodeStok = binding.kodeStok.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
                val kodeBarang = listBarang.find {
                    it?.kodeBarang == binding.kodeBarang.selectedItem.toString()
                }?.namaBarang?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
            val batasMin = binding.batasMin.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            val stok = binding.stok.text.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())

            Toast.makeText(this, "$kodeBarang", Toast.LENGTH_SHORT).show()

            sendData(

                kodeStok,
                kodeBarang,
                batasMin,
                stok
            )

        }
    }
}
            private fun sendData(
    kodeStok: RequestBody,
    kodeBarang: RequestBody?,
    batasMin: RequestBody,
    stok: RequestBody
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val res = Network().getService().tambahStok(
                kodeStok = kodeStok,
                kodeBarang = kodeBarang,
                batasMin = batasMin,
                stok = stok
            )
            MainScope().launch {
//                    dismissLoading()
                Toast.makeText(this@MainTambahStokActivity, "Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik", Toast.LENGTH_SHORT).show()
                //showMessage("Data berhasil ditambahkan. Halaman ini akan ditutup dalam 2 detik")
                Timer(true).schedule(2000) {
                    this@MainTambahStokActivity.finish()
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            MainScope().launch {
//                    dismissLoading()
                Toast.makeText(this@MainTambahStokActivity, "coba", Toast.LENGTH_SHORT).show()
                //showMessage(e.message.toString())
            }
        }
    }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(requestCode == 1000 && resultCode == Activity.RESULT_OK){
        data?.let {
            Log.d("Intent Result", "onActivityResult: $data")
        }
    }
}
fun addKodeBarang(dataBarang: List<DataBarangItem?>?) {
    val barang = mutableListOf<String?>()
    dataBarang?.let { listBarang.addAll(it) }
    dataBarang?.forEach {
        barang.add(it?.namaBarang)
    }
    val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, barang)

    binding.kodeBarang.adapter = adapter
}

private fun getListBarang(){
    CoroutineScope(Dispatchers.IO).launch {
        try{
            val response = Network().getService().getListBarang()
            MainScope().launch {
                addKodeBarang(response.dataBarang)
            }
        }catch (e: Throwable){
            e.printStackTrace()
        }
    }
}
//            val kodeBarang = binding.kodeBarang.text.toString()
//            val kodeStok = binding.kodeStok.text.toString()
//            val batasMin = binding.batasMin.text.toString()
//            val stok = binding.stok.text.toString()
//
//            val StokModel = DataStokItem(kodeBarang = kodeBarang, kodeStok = kodeStok.toInt(), batasMin = batasMin.toInt(), stok = stok.toInt())
//            val listStokModel = mutableListOf<DataStokItem?>()
//            listStokModel.add(StokModel)
//
//            adapterStok.addItem(listStokModel)
//        }
//    }
//    fun addJenisBarang(){
//    }
}