package com.example.wholettheclothesout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.net.URL


private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {

    private lateinit var sohreButton: Button
    private lateinit var pittmanButton: Button
    private lateinit var noreliusButton: Button
    private var requestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_home)
        sohreButton = findViewById(R.id.sohreButton)
        pittmanButton = findViewById(R.id.pittmanButton)
        noreliusButton = findViewById(R.id.noreliusButton)
        requestQueue = Volley.newRequestQueue(this)


        sohreButton.setOnClickListener {
            val intent = Intent(this, SohreActivity::class.java)
            startActivity(intent)
        }

        pittmanButton.setOnClickListener {
//            val intent = Intent(this, PittmanActivity::class.java)
            startActivity(intent)
        }

        noreliusButton.setOnClickListener {
//            val intent = Intent(this, NoreliusActivity::class.java)
            startActivity(intent)
        }

//        jsonParse()
    }

    private fun jsonParse() {
        Log.d(TAG, "Database")
        val url = "https://opensheet.elk.sh/1o5t26He2DzTweYeleXOGiDjlU4Jkx896f95VUHVgS8U/Test+Sheet"
        try{
            val apiResponse = URL(url).readText()
            Log.d(TAG, apiResponse)
        } finally {
            Log.d(TAG, "error")
        }


//        val url = "https://opensheet.elk.sh/1o5t26He2DzTweYeleXOGiDjlU4Jkx896f95VUHVgS8U/Test+Sheet"
//            val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
//                    response ->try {
//                val jsonArray = response.getJSONArray("")
//                for (i in 0 until jsonArray.length()) {
//                    val employee = jsonArray.getJSONObject(i)
//                    val machineName = employee.getString("MachineName")
//                    val availability = employee.getInt("availability")
//                    Log.d(TAG, "$i $machineName $availability")
//    //                textView.append("$firstName, $age, $mail\n\n")
//                }
//            } catch (e: JSONException) {
//                Log.d(TAG, "response exception")
//
//                e.printStackTrace()
//            }
//            }, Response.ErrorListener { error -> error.printStackTrace() })
//            requestQueue?.add(request)
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

