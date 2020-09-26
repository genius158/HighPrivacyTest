package com.yan.highprivacytest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.yan.highprivacy.PrivacyMgr

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 */
class TestService : Service() {
    override fun attachBaseContext(base: Context?) {
        if (!PrivacyMgr.privacy.isAuth()) {
            stopSelf()
        } else {
            super.attachBaseContext(base)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}