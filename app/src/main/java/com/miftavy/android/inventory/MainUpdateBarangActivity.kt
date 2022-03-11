package com.miftavy.android.inventory

import android.R
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.miftavy.android.inventory.databinding.ActivityMainUpdateBarangBinding
import com.miftavy.android.inventory.model.DataBarangItem
import com.miftavy.android.inventory.model.DataJenisItem
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainUpdateBarangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainUpdateBarangBinding
    private var listItem: MutableList<DataJenisItem?>? = mutableListOf()
    private lateinit var adapter: ArrayAdapter<String?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainUpdateBarangBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //set title
        supportActionBar?.title = "Update Barang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val incomingData = intent.getParcelableExtra<DataBarangItem?>("data_barang")

        getListJenis(incomingData)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setToUI(incomingData: DataBarangItem?) {
        binding.kodeBarang.setText(incomingData?.kodeBarang)
        binding.datePicker.text = incomingData?.tanggalMasuk
        binding.hargaBeli.setText(incomingData?.hargaBeli.toString())
        binding.namaBarang.setText(incomingData?.namaBarang)
        if (incomingData?.jenisBarang != null) {
            val spinnerPosition: Int = adapter.getPosition(incomingData.jenisBarang)
            binding.dropdownJenisBarang.setSelection(spinnerPosition)
        }
    }

    private fun getListJenis(incomingData: DataBarangItem?) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListJenis()
                MainScope().launch {
                    addJenisBarang(response.dataJenis, incomingData)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    private fun addJenisBarang(dataJenis: List<DataJenisItem?>?, incomingData: DataBarangItem?) {
       val jenis = mutableListOf<String?>()
        dataJenis?.let { listItem?.addAll(it) }
        dataJenis?.forEach {
            jenis.add(it?.jenisBarang)
        }
        adapter = ArrayAdapter(this, R.layout.simple_list_item_1, jenis)

        binding.dropdownJenisBarang.adapter = adapter


        setToUI(incomingData)

    }


}