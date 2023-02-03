package com.rosewhat.alerts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class User : BroadcastReceiver() {
    // все сообщения от android
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                // сообщение и on / off режим самолета
                val state = intent.getBooleanExtra(STATE, false)
                Toast.makeText(
                    context,
                    "Airplane mode $state",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(context, "Battery Low", Toast.LENGTH_SHORT).show()
            }
            PUSH_ME -> {
                // принимает значение
                val count = intent.getIntExtra(COUNT, 0)
                Toast.makeText(context, "Wakey Wakey $count", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val STATE = "state"
        const val PUSH_ME = "push"
        const val COUNT = "count"
        const val PERCENT = "percent"
    }
}