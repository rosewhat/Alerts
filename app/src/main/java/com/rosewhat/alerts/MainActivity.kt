package com.rosewhat.alerts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.rosewhat.alerts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    // получать и отправлять широковещательные сообщения в рамках одного приложения
    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    // для изменения интерфейса с помощью данных ресивера
    private val user = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra(User.PERCENT, 0)
                binding.progressBar.progress = percent
            }
        }
    }
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.alertButton.setOnClickListener {
            Intent(User.PUSH_ME).apply {
                putExtra(User.COUNT, count++)
                localBroadcastManager.sendBroadcast(this)
            }

        }
        // на какие сообщения он будет реагировать
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(User.PUSH_ME)
            addAction("loaded")
        }
        // динамическая регистрация сообщения
        localBroadcastManager.registerReceiver(user, intentFilter)
        Intent(this, Download::class.java).apply {
            startService(this)
        }
    }

    // без утечек памяти
    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(user)
    }
}