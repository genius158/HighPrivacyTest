package com.yan.highprivacytest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 */
class TestService2 : Service() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.e("TestService2", "attachBaseContext attachBaseContext attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}