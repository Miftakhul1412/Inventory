package com.miftavy.android.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftavy.android.inventory.adapter.AdapterSupplier
import com.miftavy.android.inventory.adapter.AdapterUser
import com.miftavy.android.inventory.databinding.ActivityListSupplierBinding
import com.miftavy.android.inventory.databinding.ActivityListUserBinding
import com.miftavy.android.inventory.model.ResponseListSupplier
import com.miftavy.android.inventory.model.ResponseListUser
import com.miftavy.android.inventory.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListUserActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListUserBinding
    private lateinit var adapterUser: AdapterUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize adapter barang
        adapterUser = AdapterUser {

        }
        //set title
        supportActionBar?.title = "List User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvListUser.apply {
            adapter = adapterUser
            layoutManager = LinearLayoutManager(this@ListUserActivity)
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
                val response = Network().getService().getListUser()
                //update data ke UI
                MainScope().launch {
                    updateUI(response)
                }
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: ResponseListUser) {
        response.dataUser?.let {
            adapterUser.addItem(it)
        }
    }

}