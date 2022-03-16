package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.miftavy.android.inventory.databinding.ActivityResultScanQrBinding
import com.miftavy.android.inventory.model.ResponseDetailItemBarang
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ResultScanQR : AppCompatActivity() {

    lateinit var binding: ActivityResultScanQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set title
        supportActionBar?.title = "Detail Barang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //ambil kode barang yang dikirimkan dari ScanActivity

        val kodeBarang = intent.getStringExtra("kode_barang")

        getDetail(kodeBarang)

    }

    private fun getDetail(kodeBarang: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val detailBarang = Network().getService().getDetailBarang(kodeBarang)
                //pindah ke UI THread
                MainScope().launch {
                    updateUI(detailBarang)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(detailBarang: ResponseDetailItemBarang) {
        binding.namaBarang.text = detailBarang.detail?.namaBarang
        binding.kodeBarang.text = detailBarang.detail?.kodeBarang
        binding.merek.text = detailBarang.detail?.merek
        binding.kondisi.text = detailBarang.detail?.kondisi
        binding.satuan.text = detailBarang.detail?.satuan
        binding.kodeJenisBarang.text = detailBarang.detail?.jenisBarang
        binding.tanggalMasuk.text = detailBarang.detail?.tanggalMasuk
        Glide.with(binding.root.context)
            .load("${Network().BASE_URL}${detailBarang.detail?.gambar}")
            .into(binding.gambar)
        binding.hargaBeli.text = detailBarang.detail?.hargaBeli.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        finish()
    }
}