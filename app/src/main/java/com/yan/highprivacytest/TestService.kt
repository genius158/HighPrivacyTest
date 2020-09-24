package com.yan.highprivacytest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 */
class TestService : Service() {
    override fun onCreate() {
        super.onCreate()
        Log.e("TestService", "TestService TestService TestService TestService")
        Log.e("TestService2", "TestService2 TestService2 TestService2 TestService2")
        startService(Intent(this, TestService2::class.java))
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}