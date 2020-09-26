package com.yan.highprivacytest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/26
 */
public class TestService4 extends Service {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        System.currentTimeMillis();
        Log.e("attachBaseContext", "attachBaseContext attachBaseContext");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
