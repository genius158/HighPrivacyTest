package com.yan.highprivacytest

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 */
class TestService2 : Service() {
    override fun onCreate() {
        super.onCreate()

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}