package com.example.wholettheclothesout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NoreliusActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var availabilityButton1: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norelius)
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        availabilityButton1 = findViewById(R.id.availability_w1)
        availabilityButton1.setOnClickListener {
            availabilityButton1.setEnabled(false)
            availabilityButton1.setText("IN USE")
        }

    }
}