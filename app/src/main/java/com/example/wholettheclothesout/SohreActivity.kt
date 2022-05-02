package com.example.wholettheclothesout

import SohreAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "SohreActivity"

class SohreActivity : AppCompatActivity(), SohreAdapter.OnItemClickListener {
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
            val intent = Intent(this, HomeActivity::class.java)
//            setInUse("Norelius", "Washing machine 1", "BlahBlah")
            startActivity(intent)
        }
        requestDatabase()
    }

    // you pass in the dorm name, Machine, and the status you want to set it to (open/In-use) and
    // it will change it in the data base
    fun setInUse(Dorm: String, Machine: String, status: String) {
        database = Firebase.database.reference
        database.child(Dorm).child(Machine).child("Availability").setValue(status)
    }

    private fun requestDatabase() {
        Log.d(TAG, "Successful call to database")
        FirebaseApp.initializeApp(this)
        database = Firebase.database.reference

//        database.child("Dorms").get().addOnSuccessListener {
//            val value: HashMap<String, HashMap<String, HashMap<String, String>>> = it.value as HashMap<String, HashMap<String, HashMap<String, String>>>
////            val value: HashMap<Any, Any> = it.value as HashMap<Any, Any>
//            val playersModelArrayList = ArrayList<UserModal>()
//
//            val dorm = "Sohre"
//            val machineList = value[dorm]
//            Log.d(TAG, "$dorm $machineList")
//            if (machineList != null) {
//                for (machine in machineList.keys) {
//                    val playerModel = UserModal()
//                    val machineVal = machineList[machine]
//                    val detailVal = machineVal!!["Availability"]
//
//                    Log.d(TAG, "$machine $machineVal")
//
//                    playerModel.setAvailability(detailVal.toString())
//                    playerModel.setMachineName(machine)
//                    playersModelArrayList.add(playerModel)
//                }
//                // getting the recyclerview by its id
//                val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//
//                // this creates a vertical layout Manager
//                recyclerview.layoutManager = LinearLayoutManager(this)
//
//                // This will pass the ArrayList to our Adapter
//                val adapter = SohreAdapter(playersModelArrayList)
//
//                // Setting the Adapter with the recyclerview
//                recyclerview.adapter = adapter
//            }
//        }.addOnFailureListener{
//            Log.d(TAG, "Error getting data", it)
//        }

        Log.d(TAG, "$database")

        val dormListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val playersModelArrayList = ArrayList<UserModal>()

                // Get Post object and use the values to update the UI
//                val dormSnapshot = dataSnapshot.getValue()
//                Log.d(TAG, "Data was updated $dormSnapshot")

                val value: HashMap<String, HashMap<String, HashMap<String, String>>> = dataSnapshot.child("Dorms").getValue() as HashMap<String, HashMap<String, HashMap<String, String>>>
//            val value: HashMap<Any, Any> = it.value as HashMap<Any, Any>
                Log.d(TAG, "Data was updated $value")

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
                    setView(playersModelArrayList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }


        database.addValueEventListener(dormListener)

//        setInUse("Pittman", "Washing machine 1", "blah")

        // The code below is to get data once from the database
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Get Post object and use the values to update the UI
//                val post = dataSnapshot.getValue<Post>()
//                // ...
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        postReference.addValueEventListener(postListener)

    }

    private fun setView(modal: ArrayList<UserModal>) {
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        val adapter = SohreAdapter(modal,this)
        Log.d(TAG, "$modal")

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val playersModelArrayList = ArrayList<UserModal>()
        Log.d(TAG, "Item $position")
        val clickedItem: UserModal = playersModelArrayList[position]
        Log.d(TAG, "${clickedItem.getMachineName}")
    }

    private fun requestJSON() {
        // Everything below is excel
//        val url = "https://opensheet.elk.sh/1rmxb77EULnkJ3DXygAB3rYzfSVhW5yXiMW0lOL1Dszk/Sheet1"
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            { response ->
////                Log.d(TAG, ">> $response")
//                try {
//                    //Excel Data Extraction
//                    val playersModelArrayList = ArrayList<UserModal>()
//                    val dataArray = JSONArray(response)
////                    Log.d(TAG, "$dataArray")
//                    for (i in 0 until dataArray.length()) {
//                        val playerModel = UserModal()
//                        val dataobj = dataArray.getJSONObject(i)
////                        Log.d(TAG, "$dataobj")
//                        machineName = dataobj.getString("MachineName")
//                        availability = dataobj.getString("Availability")
//                        playerModel.setMachineName(machineName)
//                        playerModel.setAvailability(availability)
//                        playersModelArrayList.add(playerModel)
////                        Log.d(TAG, "${playerModel.getMachineName}")
//                    }
////                    for (element in playersModelArrayList) {
////                        Log.d(TAG, "${element.getAvailability}")
////                        Log.d(TAG, "${element.getMachineName}")
////                    }
//                    // getting the recyclerview by its id
//                    val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//                    // this creates a vertical layout Manager
//                    recyclerview.layoutManager = LinearLayoutManager(this)
//                    // This will pass the ArrayList to our Adapter
//                    val adapter = SohreAdapter(playersModelArrayList)
//                    // Setting the Adapter with the recyclerview
//                    recyclerview.adapter = adapter
//                } catch (e: JSONException) {
//                    Log.d(TAG, "Failed")
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