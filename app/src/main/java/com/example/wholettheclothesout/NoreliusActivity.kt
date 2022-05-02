package com.example.wholettheclothesout

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.wholettheclothesout.util.PrefUtil2
import java.util.*
private const val TAG = "NoreliusActivity"

class NoreliusActivity : AppCompatActivity() {

    companion object{
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long{
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver2::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime,pendingIntent)
            PrefUtil2.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReceiver2::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil2.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }

    enum class TimerState{
        Stopped, Running
    }

    private lateinit var backButton: Button
    private lateinit var timer: CountDownTimer
    private var timerLength = 30L
    private var timeRemaining = 0L
    private lateinit var startButton: Button
    private var timerState = TimerState.Stopped
    private lateinit var timertest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_norelius)
        timertest = findViewById(R.id.timertest2)
        timertest.text = ""
        startButton = findViewById(R.id.startButton2)
        backButton = findViewById(R.id.backButton2)

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        startButton.setOnClickListener{
            startTimer()
            updateButtons()
            timerState = TimerState.Running

        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume")

        initTimer()

        removeAlarm(this)
    }

    override fun onPause() {
        super.onPause()
        if(timerState == TimerState.Running){
            timer.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, timeRemaining)
        }

        PrefUtil2.setPreviousTimeLengthSeconds(timerLength, this)
        PrefUtil2.setSecondsRemaining(timeRemaining, this)
        PrefUtil2.setTimerState(timerState, this)
    }

    private fun initTimer(){
        timerState = PrefUtil2.getTimerState(this)

        if(timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        timeRemaining = if (timerState == TimerState.Running)
            PrefUtil2.getSecondsRemaining(this)
        else
            timerLength

        val alarmSetTime = PrefUtil2.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            timeRemaining -= nowSeconds - alarmSetTime

        if (timeRemaining <= 0)
            onTimerFinished()
        else if(timerState == TimerState.Running)
            startTimer()

        updateCountDownUI()
        updateButtons()
    }

    private fun onTimerFinished(){
        timerState = TimerState.Stopped
        setNewTimerLength()
        PrefUtil2.setSecondsRemaining(timerLength,this)
        timeRemaining = timerLength
        updateCountDownUI()
        updateButtons()
    }

    private fun startTimer(){
        timerState = TimerState.Running

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
        timerLength = PrefUtil2.getPreviousTimerLengthSeconds(this)
    }

    private fun updateCountDownUI(){
        val secondsUntilFinished = timeRemaining
        val timeStr = secondsUntilFinished.toString()
        timertest.text = timeStr
    }

    private fun updateButtons(){
        when(timerState){
            TimerState.Running ->{
                startButton.setEnabled(false)
                startButton.text = "In-Use"
            }
            TimerState.Stopped ->{
                startButton.setEnabled(true)
                startButton.text = "Open"
                timertest.text = ""
            }
        }

    }


}
