package com.yan.highprivacytest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/25
 */
public class TestService3 extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
