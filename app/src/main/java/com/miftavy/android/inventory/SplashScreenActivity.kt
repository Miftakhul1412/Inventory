package com.miftavy.android.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler.postDelayed({
            //menampilkan setelah splash ke mainmenuactivity
            Intent(this@SplashScreenActivity, MainLogin::class.java).apply {
                startActivity(this)
                finish()
            }
        }, 3000)
    }

    override fun onStop() {
        super.onStop()
        //cancel handler nya
        handler.removeCallbacksAndMessages(null)
    }
}