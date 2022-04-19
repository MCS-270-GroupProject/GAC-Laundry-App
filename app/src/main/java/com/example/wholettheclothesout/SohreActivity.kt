package com.example.wholettheclothesout

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SohreActivity : AppCompatActivity() {
    //var counter = 60000
    var grace = 30000
    private lateinit var backButton: Button
    private lateinit var button: Button
    var string = ""

    fun done(){
        button.setEnabled(true)
        button.setText("OPEN")
        val editText : EditText = findViewById(R.id.countTime)
        editText.isEnabled = true
        //counter = 60000
    }

    fun startGraceCounter() {
        val gracePeriod: TextView = findViewById(R.id.gracePeriod)
        val countTime: TextView = findViewById(R.id.countTime)
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                gracePeriod.text = (grace/1000).toString()
                grace-=1000
            }
            override fun onFinish() {
                gracePeriod.text = ""
                countTime.text = ""
                done()
            }
        }.start()

    }

    fun startTimeCounter(time: Long) {
        var counter = time*1000
        val countTime: EditText = findViewById(R.id.countTime)
        object : CountDownTimer(time*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countTime.setText((counter/1000).toString())
                counter-=1000
            }
            override fun onFinish() {
                countTime.setText("Done")
                startGraceCounter()
            }
        }.start()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sohre)
        title = "KotlinApp"
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        button = findViewById(R.id.button)
        button.setOnClickListener {
            button.setEnabled(false)
            button.setText("IN USE")
            val editText : EditText = findViewById(R.id.countTime)
            editText.isEnabled = false
            string = editText.text.toString()
            startTimeCounter(string.toLong())
        }

    }

}