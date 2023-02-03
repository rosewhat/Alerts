package com.rosewhat.alerts

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.concurrent.thread

class Download : Service() {
    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1..10) {
                Thread.sleep(1000)
                Intent(DOWNLOAD_PERCENT).apply {
                    putExtra("percent", i * 10)
                    //получает андроид, получают все приложения
                    localBroadcastManager.sendBroadcast(this)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        const val DOWNLOAD_PERCENT = "loaded"
    }
}