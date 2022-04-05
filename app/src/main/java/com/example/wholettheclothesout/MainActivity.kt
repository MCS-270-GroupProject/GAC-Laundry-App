package com.example.wholettheclothesout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var sohreButton: Button
    private lateinit var pittmanButton: Button
    private lateinit var noreliusButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        sohreButton = findViewById(R.id.sohreButton)
        pittmanButton = findViewById(R.id.pittmanButton)
        noreliusButton = findViewById(R.id.noreliusButton)


        sohreButton.setOnClickListener {
            val intent = Intent(this, SohreActivity::class.java)
            startActivity(intent)
        }

        pittmanButton.setOnClickListener {
            val intent = Intent(this, PittmanActivity::class.java)
            startActivity(intent)
        }

        noreliusButton.setOnClickListener {
            val intent = Intent(this, NoreliusActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}

