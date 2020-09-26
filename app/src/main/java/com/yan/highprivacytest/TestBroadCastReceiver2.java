package com.yan.highprivacytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/26
 */
public class TestBroadCastReceiver2 extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive","TestBroadCastReceiver2 TestBroadCastReceiver2");
    }
}
