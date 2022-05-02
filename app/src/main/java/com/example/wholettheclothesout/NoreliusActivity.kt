//package com.example.wholettheclothesout
//
//import SohreAdapter
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.android.volley.Request
//import com.android.volley.RequestQueue
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.google.firebase.FirebaseApp
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import org.json.JSONArray
//import org.json.JSONException
//
//private const val TAG = "SohreActivity"
//
//class NoreliusActivity : AppCompatActivity(), SohreAdapter.OnItemClickListener {
//    private var requestQueue: RequestQueue? = null
//    private lateinit var machineName: String
//    private lateinit var availability: String
//
//    private lateinit var backButton: Button
//    private lateinit var useButton: Button
//    private lateinit var countTime: EditText
//    private lateinit var gracePeriod: TextView
//
//    private lateinit var database: DatabaseReference
//
//    var grace = 30000
//    var string = ""
//    var counter = 0.toLong()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sohre)
//        Log.d(TAG, "successful oncreate")
//        requestQueue = Volley.newRequestQueue(this)
//
////        countTime = findViewById(R.id.countTime)
//        backButton = findViewById(R.id.backButton)
////        gracePeriod = findViewById(R.id.gracePeriod)
//
//        backButton.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//
//        requestJSON()
//
//        title = "KotlinApp"
//
////        useButton.setOnClickListener {
////            useButton.setEnabled(false)
////            useButton.setText("IN USE")
////            countTime.isEnabled = false
////            string = countTime.text.toString()
////            startTimeCounter(string.toLong())
////        }
//
//
//    }
//
////    private fun onListItemClick(position: Int) {
////        Toast.makeText(this, mRepos[position].name, Toast.LENGTH_SHORT).show()
////    }
//
////    fun done(){
////        useButton.setEnabled(true)
////        useButton.setText("OPEN")
////        countTime.isEnabled = true
////        counter = 60000
////    }
//
////    fun startGraceCounter() {
////        object : CountDownTimer(30000, 1000) {
////            override fun onTick(millisUntilFinished: Long) {
////                gracePeriod.text = (grace/1000).toString()
////                grace-=1000
////            }
////            override fun onFinish() {
////                gracePeriod.text = ""
////                countTime.setText("")
////                done()
////            }
////        }.start()
////    }
//
////    fun startTimeCounter(time: Long) {
////        counter = time*1000
////        object : CountDownTimer(time*1000, 1000) {
////            override fun onTick(millisUntilFinished: Long) {
////                countTime.setText((counter/1000).toString())
////                counter-=1000
////            }
////            override fun onFinish() {
////                countTime.setText("Done")
////                startGraceCounter()
////            }
////        }.start()
////    }
//
//
//    private fun requestJSON() {
//        FirebaseApp.initializeApp(this)
//        database = FirebaseDatabase.getInstance().getReference("0").child("MachineName")
//        Log.d(TAG, "$database")
//
//        // Everything below is excel
//        val url = "https://opensheet.elk.sh/1rmxb77EULnkJ3DXygAB3rYzfSVhW5yXiMW0lOL1Dszk/Sheet1"
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            { response ->
//                Log.d(TAG, ">> $response")
//
//                try {
//
//                    //Excel Data Extraction
//                    val playersModelArrayList = ArrayList<UserModal>()
//                    val dataArray = JSONArray(response)
////                    Log.d(TAG, "$dataArray")
//
//                    for (i in 0 until dataArray.length()) {
//                        Log.d(TAG, "$i")
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
//
//                    for (element in playersModelArrayList) {
//                        Log.d(TAG, "${element.getAvailability}")
//                        Log.d(TAG, "${element.getMachineName}")
//                    }
//
//
//                    // getting the recyclerview by its id
//                    val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//
//                    // this creates a vertical layout Manager
//                    recyclerview.layoutManager = LinearLayoutManager(this)
//
//                    // This will pass the ArrayList to our Adapter
//                    val adapter = SohreAdapter(playersModelArrayList, this)
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
//    }
//}