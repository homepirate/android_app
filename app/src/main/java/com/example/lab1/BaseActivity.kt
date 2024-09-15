package com.example.lab1

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        Log.d(this::class.simpleName, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(this::class.simpleName, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(this::class.simpleName, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(this::class.simpleName, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this::class.simpleName, "onDestroy called")
    }
}