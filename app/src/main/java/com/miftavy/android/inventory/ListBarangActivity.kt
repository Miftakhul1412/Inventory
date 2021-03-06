package com.miftavy.android.inventory

import android.R
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterBarang
import com.miftavy.android.inventory.databinding.ActivityListBarangBinding
import com.miftavy.android.inventory.model.*
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import androidx.appcompat.widget.SearchView
import kotlin.contracts.Returns


class ListBarangActivity: AppCompatActivity(){
    private lateinit var binding: ActivityListBarangBinding
    private lateinit var adapterBarang : AdapterBarang
    private  var filterIdJenisBarang : String = ""

    private var listJenis = mutableListOf<DataJenisItem?>()
//    private var listJenis = mutableListOf<LihatItem?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getListJenis()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        val response = Network().getService().getCariBarang(query)
                        //update data ke UI
                        MainScope().launch {
                            adapterBarang.addItem(mutableListOf(), true)
                            response.dataBarang?.let {
                                adapterBarang.addItem(it, true)
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
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        //set title
        supportActionBar?.title = "List Barang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //initialize adapter barang


        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false

            makeRequest()
        }
    binding.dropdownJenisBarang?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
//            binding.dropdownNamaSupplier?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position == 0){
                filterIdJenisBarang = ""
                makeRequest()
            }else {
                var jenis = listJenis.get(position-1)
                filterIdJenisBarang = jenis?.idJenisBarang.toString()
                makeRequest()
            }
       }
    }

        initAdapter()

        binding.rvListBarang.apply {
            adapter = adapterBarang
            layoutManager = LinearLayoutManager(this@ListBarangActivity)
        }


        makeRequest()
    }

    private fun initAdapter(){
        adapterBarang = AdapterBarang(AdapterBarang.LINEARTYPE) {
            val showDialogDetailBarang = DetailBarangActivity.newInstance(it)
            showDialogDetailBarang.show(supportFragmentManager, "detail")
        }
    }
//    private fun getLatestNews() {
//        //panggil Retrofit nya ya
//        Network().getService()
//            .getLatestNews()
//            .enqueue(object : Callback<ResponseListBarang> {
//                override fun onResponse(
//                    call: Call<ResponseListBarang>,
//                    response: Response<ResponseListBarang>
//                ) {
//                    this@ListBarangActivity.binding.progressIndicator.visibility = View.GONE
//                    if (response.isSuccessful) {
//                        val receivedDatas = response.body()?.dataBarang
//                        setToAdapter(receivedDatas)
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseListBarang>, t: Throwable) {
//                    this@ListBarangActivity.binding.progressIndicator.visibility = View.GONE
//                    Log.d("Retrofit: onFailure: ", "onFailure: ${t.stackTrace}")
//                }
//            })
//    }
//    private fun setToAdapter(receivedDatas: List<DataBarangItem?>?) {
//        //reset adapter first
//        this.binding.rvListBarang.adapter = null
//
//        val adapter = AdapterBarang(receivedDatas) {
//            val detailNewsIntent = Intent(this@ListBarangActivity, DetailBarangActivity::class.java)
//            detailNewsIntent.putExtra("idNews", it?.kodeBarang)
//            detailNewsIntent.putExtra("judulSeo", it?.namaBarang)
//            startActivity(detailNewsIntent)
//        }
//        val lm = LinearLayoutManager(this)
//        this.binding.rvListBarang.layoutManager = lm
//        this.binding.rvListBarang.itemAnimator = DefaultItemAnimator()
//        this.binding.rvListBarang.adapter = adapter
//
//
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    fun makeRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListBarang(filterIdJenisBarang)
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
    fun addJenisBarang(dataJenis: List<DataJenisItem?>?) {
        val jenis = mutableListOf<String?>()
        jenis.add(0, "semua")
        dataJenis?.let { listJenis.addAll(it) }
        dataJenis?.forEachIndexed { index, dataJenisItem ->
            jenis.add(index+1, dataJenisItem?.jenisBarang)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jenis)

        binding.dropdownJenisBarang.adapter = adapter
    }

    private fun getListJenis(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = Network().getService().getListJenis()
                MainScope().launch {
                    addJenisBarang(response.dataJenis)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }
}


