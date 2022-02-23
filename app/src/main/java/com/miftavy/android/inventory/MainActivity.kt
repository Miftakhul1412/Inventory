package com.miftavy.android.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.miftavy.android.inventory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signIn.setOnClickListener {
//            Toast.makeText(this, binding.username.text.toString(), Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, binding.password.text.toString(), Toast.LENGTH_SHORT).show()
            if(binding.username.getText().toString().equals(binding.password.getText().toString())){
//                Toast.makeText(this,"Selamat Datang ,${binding.username.getText().toString()}", Toast.LENGTH_LONG).show()
                 Intent(this@MainActivity, MainMenuActivity::class.java).apply {
                     startActivity(this)
                 }
            }else{
                Toast.makeText(this,"Password Salah", Toast.LENGTH_LONG).show()

            }



        }
    }
}