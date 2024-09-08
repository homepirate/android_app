package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class OnboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

}
