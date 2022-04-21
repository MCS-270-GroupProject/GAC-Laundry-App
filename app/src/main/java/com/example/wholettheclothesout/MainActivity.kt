package com.example.wholettheclothesout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import layout.UserModal
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var sohreButton: Button
    private lateinit var pittmanButton: Button
    private lateinit var noreliusButton: Button
    private var requestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        sohreButton = findViewById(R.id.sohreButton)
        pittmanButton = findViewById(R.id.pittmanButton)
        noreliusButton = findViewById(R.id.noreliusButton)
        requestQueue = Volley.newRequestQueue(this)


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

