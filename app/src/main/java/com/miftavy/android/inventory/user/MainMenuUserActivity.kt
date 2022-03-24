package com.miftavy.android.inventory.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.miftavy.android.inventory.ListBarangActivity
import com.miftavy.android.inventory.R
import com.miftavy.android.inventory.ScanActivity
import com.miftavy.android.inventory.databinding.ActivityMainMenuUserBinding
import com.miftavy.android.inventory.fragment.HistoryBarangFragment
import com.miftavy.android.inventory.fragment.HomeFragment
import com.miftavy.android.inventory.fragment.ListBarangFragment
import com.miftavy.android.inventory.fragment.ProfilFragment

class MainMenuUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainMenuUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, HomeFragment()).commit()


        binding.bottomnav.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home ->{
//                    saat menu home diklkik, action -> fragmenthome\
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, HomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.brg ->{
//                    ngapain setelah diklik mwnuakun
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, ListBarangActivity()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.history ->{
//                    ngapain setelah diklik mwnuakun
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, HistoryBarangFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.profil ->{
//                    ngapain setelah diklik mwnuakun
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, ProfilFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }

            return@setOnItemSelectedListener false
        }

        binding.qr.setOnClickListener {
            Intent(this@MainMenuUserActivity, ScanActivity::class.java).apply {
                startActivity(this)
            }
        }


    }
}
