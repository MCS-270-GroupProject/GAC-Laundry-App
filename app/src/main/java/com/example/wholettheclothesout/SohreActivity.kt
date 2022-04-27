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
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.core.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import layout.UserModal
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.log

private const val TAG = "SohreActivity"

class SohreActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private var requestQueue: RequestQueue? = null
    private lateinit var machineName: String
    private lateinit var availability: String
    private lateinit var database: DatabaseReference
    private lateinit var listener: ChildEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sohre)
        Log.d(TAG, "successful oncreate")
        requestQueue = Volley.newRequestQueue(this)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setInUse("Norelius", "Washing machine 1", "BlahBlah")
            startActivity(intent)

        }
        requestJSON()
    }
    // you pass in the dorm name, Machine, and the status you want to set it to (open/In-use) and it will change it in the data base
    fun setInUse(Dorm: String, Machine: String, status: String) {
        database = Firebase.database.reference
        database.child(Dorm).child(Machine).child("Availability").setValue(status)
    }

    private fun requestJSON() {
        FirebaseApp.initializeApp(this)
        database = Firebase.database.reference
        database.child("Dorms").get().addOnSuccessListener {
            val value: HashMap<String, HashMap<String, HashMap<String, String>>> = it.value as HashMap<String, HashMap<String, HashMap<String, String>>>
//            val value: HashMap<Any, Any> = it.value as HashMap<Any, Any>
            val playersModelArrayList = ArrayList<UserModal>()

            val dorm = "Sohre"
            val machineList = value[dorm]
            Log.d(TAG, "$dorm $machineList")
            if (machineList != null) {
                for (machine in machineList.keys) {
                    val playerModel = UserModal()
                    val machineVal = machineList[machine]
                    val detailVal = machineVal!!["Availability"]

                    Log.d(TAG, "$machine $machineVal")

                    playerModel.setAvailability(detailVal.toString())
                    playerModel.setMachineName(machine)
                    playersModelArrayList.add(playerModel)
                }
                // getting the recyclerview by its id
                val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

                // this creates a vertical layout Manager
                recyclerview.layoutManager = LinearLayoutManager(this)

                // This will pass the ArrayList to our Adapter
                val adapter = SohreAdapter(playersModelArrayList)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
            }
        }.addOnFailureListener{
            Log.d(TAG, "Error getting data", it)
        }

        Log.d(TAG, "$database")
//        setInUse("Pittman", "Washing machine 1", "blah")


        // Everything below is excel

//        val url = "https://opensheet.elk.sh/1rmxb77EULnkJ3DXygAB3rYzfSVhW5yXiMW0lOL1Dszk/Sheet1"
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            { response ->
////                Log.d(TAG, ">> $response")
//
//                try {
//
//                    //Excel Data Extraction
//                    val playersModelArrayList = ArrayList<UserModal>()
//                    val dataArray = JSONArray(response)
////                    Log.d(TAG, "$dataArray")
//
//                    for (i in 0 until dataArray.length()) {
//                        val playerModel = UserModal()
//                        val dataobj = dataArray.getJSONObject(i)
////                        Log.d(TAG, "$dataobj")
//                        machineName = dataobj.getString("MachineName")
//                        availability = dataobj.getString("Availability")
//
//                        playerModel.setMachineName(machineName)
//                        playerModel.setAvailability(availability)
//                        playersModelArrayList.add(playerModel)
////                        Log.d(TAG, "${playerModel.getMachineName}")
//
//                    }
////
////                    for (element in playersModelArrayList) {
////                        Log.d(TAG, "${element.getAvailability}")
////                        Log.d(TAG, "${element.getMachineName}")
////                    }
//
//
//                    // getting the recyclerview by its id
//                    val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//
//                    // this creates a vertical layout Manager
//                    recyclerview.layoutManager = LinearLayoutManager(this)
//
//                    // This will pass the ArrayList to our Adapter
//                    val adapter = SohreAdapter(playersModelArrayList)
//
//                    // Setting the Adapter with the recyclerview
//                    recyclerview.adapter = adapter
//
//                } catch (e: JSONException) {
//                    Log.d(TAG, "Failed")
//
//                    e.printStackTrace()
//                }
//            },
//            { error ->
//                //displaying the error in toast if occurrs
//                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
//            })
//        // request queue
//        val requestQueue = Volley.newRequestQueue(this)
//        requestQueue.add(stringRequest)
    }
}