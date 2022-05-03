package com.example.wholettheclothesout

import NoreliusAdapter
import SohreAdapter
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.wholettheclothesout.util.PrefUtil3
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


private const val TAG = "SohreActivity"

class SohreActivity : AppCompatActivity(), SohreAdapter.OnItemClickListener {

    companion object{
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long{
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver3::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime,pendingIntent)
            PrefUtil3.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReceiver3::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil3.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }

    enum class TimerState{
        Stopped, Running
    }


    private lateinit var timer: CountDownTimer
    private var timerLength = 30L
    private var timeRemaining = 0L
    //private lateinit var startButton: Button
    private var timerState = TimerState.Stopped
    //private lateinit var timertest: TextView

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

        /*start timer when button is clicked
        startButton.setOnClickListener{
            startTimer()
            //updateButtons()
            timerState = SohreActivity.TimerState.Running

        }*/

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
                        playerModel.setCountTime(machineVal["Timer"].toString())
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

    /*override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume")

        initTimer()

        SohreActivity.removeAlarm(this)
    }

    override fun onPause() {
        super.onPause()
        if(timerState == SohreActivity.TimerState.Running){
            timer.cancel()
            val wakeUpTime =
                SohreActivity.setAlarm(this, SohreActivity.nowSeconds, timeRemaining)
        }

        PrefUtil3.setPreviousTimeLengthSeconds(timerLength, this)
        PrefUtil3.setSecondsRemaining(timeRemaining, this)
        PrefUtil3.setTimerState(timerState, this)
    }*/

    private fun initTimer(){
        timerState = PrefUtil3.getTimerState(this)

        if(timerState == SohreActivity.TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        timeRemaining = if (timerState == SohreActivity.TimerState.Running)
            PrefUtil3.getSecondsRemaining(this)
        else
            timerLength

        val alarmSetTime = PrefUtil3.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            timeRemaining -= SohreActivity.nowSeconds - alarmSetTime

        if (timeRemaining <= 0)
            onTimerFinished()
        else if(timerState == SohreActivity.TimerState.Running)
            startTimer()

        updateCountDownUI()
        //updateButtons()
    }

    private fun onTimerFinished(){
        timerState = SohreActivity.TimerState.Stopped
        setNewTimerLength()
        PrefUtil3.setSecondsRemaining(timerLength,this)
        timeRemaining = timerLength
        updateCountDownUI()
        //updateButtons()
    }

    private fun startTimer(){
        timerState = SohreActivity.TimerState.Running

        timer = object : CountDownTimer(timeRemaining*1000,1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished / 1000
                updateCountDownUI()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        timerLength = 30L
    }

    private fun setPreviousTimerLength(){
        timerLength = PrefUtil3.getPreviousTimerLengthSeconds(this)
    }

    private fun updateCountDownUI(){
        val secondsUntilFinished = timeRemaining
        val timeStr = secondsUntilFinished.toString()
        //timertest.text = timeStr, set text view to display timer
    }

    /*private fun updateButtons(){
        when(timerState){
            SohreActivity.TimerState.Running ->{
                startButton.setEnabled(false)
                startButton.text = "In-Use"
            }
            SohreActivity.TimerState.Stopped ->{
                startButton.setEnabled(true)
                startButton.text = "Open"
                timertest.text = ""
            }
        }

    }*/

}