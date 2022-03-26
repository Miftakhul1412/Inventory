package com.miftavy.android.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.miftavy.android.inventory.databinding.ActivityMainBinding
import com.miftavy.android.inventory.input.InputLogin
import com.miftavy.android.inventory.network.Network
import com.miftavy.android.inventory.user.MainMenuUserActivity
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signIn.setOnClickListener {
//            Toast.makeText(this, binding.username.text.toString(), Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, binding.password.text.toString(), Toast.LENGTH_SHORT).show()
            if (binding.username.text.toString().isNotEmpty() && binding.password.text.toString().isNotEmpty()
            ) {
//                Toast.makeText(this,"Selamat Datang ,${binding.username.getText().toString()}", Toast.LENGTH_LONG).show()
//                 Intent(this@MainActivity, MainMenuActivity::class.java).apply {
//                     startActivity(this)
//                 }
                //create input login
                val input = InputLogin()
                input.userid = binding.username.text.toString()
                input.password = binding.password.text.toString()

                makeRequest(input)
            } else {
                Toast.makeText(this, "Password Salah", Toast.LENGTH_LONG).show()

            }


        }
    }

    private fun makeRequest(inputLogin: InputLogin) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = Network().getService().getLogin(inputLogin)
                MainScope().launch {
                    Prefs.putString("nama", response.name)
                    Prefs.putString("email", response.email)
                    Prefs.putString("level", response.level)


                    Intent(this@MainActivity, MainMenuUserActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}