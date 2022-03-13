package com.miftavy.android.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.miftavy.android.inventory.databinding.ActivityMainMenuBinding
import com.miftavy.android.inventory.model.ResponseListBarang
import com.miftavy.android.inventory.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainMenuActivity : AppCompatActivity() {
    //deklarasi variabel tanpa ngasi nilai awal
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.scn.setOnClickListener {
                Intent(this@MainMenuActivity, ScanActivity::class.java).apply {
                    startActivity(this)
                }
            }

            binding.listSupplier.setOnClickListener {
                Intent(this@MainMenuActivity, ListSupplierActivity::class.java).apply {
                    startActivity(this)
                }
            }
        binding.user.setOnClickListener {
            Intent(this@MainMenuActivity, ListUserActivity::class.java).apply {
                startActivity(this)
            }
        }
//            binding.listBarangKeluar.setOnClickListener {
//                Intent(this@MainMenuActivity, MainTambahJenisBarangActivity::class.java).apply {
//                    startActivity(this)
//                }
//            }
            binding.lihatbarang.setOnClickListener {
                Intent(this@MainMenuActivity, ListBarangActivity::class.java).apply {
                    startActivity(this)
                }
            }

            binding.lihatStok.setOnClickListener {
                Intent(this@MainMenuActivity, ListStokActivity::class.java).apply {
                    startActivity(this)
                }
            }
            binding.lihatjenis.setOnClickListener {
                Intent(this@MainMenuActivity, ListJenisActivity::class.java).apply {
                    startActivity(this)
                }
            }
    }
//    private fun searchBarang(query: String?) {
//            show progressbar first
//            this.binding.progressIndicator.visibility = View.VISIBLE
//            Network()
//                .getService()
//                .searchBarang(query)
//                .enqueue(object : Callback<ResponseListBarang> {
//                    override fun onResponse(
//                        call: Call<ResponseListBarang>,
//                        response: Response<ResponseListBarang>
//                    ) {
//                        this@MainMenuActivity.binding.progressIndicator.visibility = View.GONE
//                        if (response.isSuccessful) {
//                            val receivedDatas = response.body()?.data
//                            setToAdapter(receivedDatas)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ResponseListBarang>, t: Throwable) {
//                        this@MainMenuActivity.binding.progressIndicator.visibility = View.GONE
//                        Log.d("Retrofit: onFailure: ", "onFailure: ${t.stackTrace}")
//                    }
//                })
//        }
}