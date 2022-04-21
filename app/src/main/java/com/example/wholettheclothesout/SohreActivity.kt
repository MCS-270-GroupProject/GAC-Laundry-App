package com.example.wholettheclothesout

import SohreAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import layout.UserModal
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "SohreActivity"

class SohreActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private var requestQueue: RequestQueue? = null
    private lateinit var machineName: String
    private lateinit var availability: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sohre)
        Log.d(TAG, "successful oncreate")

        requestQueue = Volley.newRequestQueue(this)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        requestJSON()
    }

    private fun requestJSON() {
        val url = "https://opensheet.elk.sh/1rmxb77EULnkJ3DXygAB3rYzfSVhW5yXiMW0lOL1Dszk/Sheet1"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d(TAG, ">> $response")

                try {
                    val playersModelArrayList = ArrayList<UserModal>()
                    val dataArray = JSONArray(response)
//                    Log.d(TAG, "$dataArray")

                    for (i in 0 until dataArray.length()) {
                        Log.d(TAG, "$i")
                        val playerModel = UserModal()
                        val dataobj = dataArray.getJSONObject(i)
//                        Log.d(TAG, "$dataobj")
                        machineName = dataobj.getString("MachineName")
                        availability = dataobj.getString("Availability")

                        playerModel.setMachineName(machineName)
                        playerModel.setAvailability(availability)
                        playersModelArrayList.add(playerModel)
//                        Log.d(TAG, "${playerModel.getMachineName}")

                    }

                    for (element in playersModelArrayList) {
                        Log.d(TAG, "${element.getAvailability}")
                        Log.d(TAG, "${element.getMachineName}")
                    }

                    // getting the recyclerview by its id
                    val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

                    // this creates a vertical layout Manager
                    recyclerview.layoutManager = LinearLayoutManager(this)

                    // This will pass the ArrayList to our Adapter
                    val adapter = SohreAdapter(playersModelArrayList)

                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter

                } catch (e: JSONException) {
                    Log.d(TAG, "Failed")

                    e.printStackTrace()
                }
            },
            { error ->
                //displaying the error in toast if occurrs
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            })
        // request queue
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

}