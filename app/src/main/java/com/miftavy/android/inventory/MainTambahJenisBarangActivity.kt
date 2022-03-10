package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.*
import com.miftavy.android.inventory.databinding.ActivityMainTambahJenisBarangBinding
import com.miftavy.android.inventory.model.DataJenisItem
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import kotlin.concurrent.schedule

class MainTambahJenisBarangActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainTambahJenisBarangBinding
    private  lateinit var adapterJenis: AdapterJenis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainTambahJenisBarangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //adapterBarang = AdapterBarang()
        adapterJenis = AdapterJenis{
            Toast.makeText(this, it?.jenisBarang, Toast.LENGTH_SHORT).show()
        }

        //set title
        supportActionBar?.title = "Tambah Jenis"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvJenis.apply {
            adapter = adapterJenis
            layoutManager = LinearLayoutManager(this@MainTambahJenisBarangActivity)
        }

//        kasih klik untuk tombol simpannya
        binding.simpanData.setOnClickListener {
            if (binding.jenisBarang.text.toString()
                    .isEmpty() || binding.idJenisBarang.text.toString().isEmpty()

            ) {
                Toast.makeText(this, "Isi dan pilih semua terlebih dahulu", Toast.LENGTH_SHORT).show()
                //showMessage("Isi dan pilih semua terlebih dahulu")
            } else {
//                showLoading()
                val idJenisBarang = binding.idJenisBarang.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val jenisBarang = binding.jenisBarang.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())


                Toast.makeText(this, "$jenisBarang", Toast.LENGTH_SHORT).show()

                sendData(

                    idJenisBarang,
                    jenisBarang

                )
            }

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun sendData(
        idJenisBarang: RequestBody,
        jenisBarang: RequestBody
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = Network().getService().tambahJenis(
                    idJenisBarang = idJenisBarang,
                    jenisBarang = jenisBarang
                )
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(this@MainTambahJenisBarangActivity, "Data berhasil ditambahkan. Halaman ini akan ditutup dalam 5 detik", Toast.LENGTH_SHORT).show()
                    //showMessage("Data berhasil ditambahkan. Halaman ini akan ditutup dalam 5 detik")
                    Timer(true).schedule(5000) {
                        this@MainTambahJenisBarangActivity.finish()
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                MainScope().launch {
//                    dismissLoading()
                    Toast.makeText(this@MainTambahJenisBarangActivity, "Gagal ", Toast.LENGTH_SHORT).show()
                    //showMessage(e.message.toString())
                }
            }
        }
    }
}