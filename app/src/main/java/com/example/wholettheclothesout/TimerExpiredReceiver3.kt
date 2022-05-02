package com.example.wholettheclothesout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wholettheclothesout.util.PrefUtil3

class TimerExpiredReceiver3 : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        PrefUtil3.setTimerState(SohreActivity.TimerState.Stopped, context)
        PrefUtil3.setAlarmSetTime(0, context)
    }
}