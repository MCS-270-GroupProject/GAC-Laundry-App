package com.example.wholettheclothesout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.wholettheclothesout.util.PrefUtil2

class TimerExpiredReceiver2 : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        PrefUtil2.setTimerState(NoreliusActivity.TimerState.Stopped, context)
        PrefUtil2.setAlarmSetTime(0, context)
    }
}