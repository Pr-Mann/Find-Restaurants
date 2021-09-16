package com.priyank.findrestaurants.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.priyank.findrestaurants.R
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(3000) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
    override fun onBackPressed() {

    }
}