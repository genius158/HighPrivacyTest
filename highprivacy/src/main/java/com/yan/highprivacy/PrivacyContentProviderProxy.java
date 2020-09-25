package com.yan.highprivacy;

import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.ProviderInfo;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/24
 */
public abstract class PrivacyContentProviderProxy extends ContentProvider implements CallOriginalAttachInfoFromPrivacy {
    @Override
    public void attachInfo(final Context context, final ProviderInfo info) {
        if (PrivacyMgr.getPrivacy().isAuth()) {
            callOriginalAttachInfoFromPrivacy(context, info);
            return;
        }
        ExtraAuthDispatcher.getDispatcher().observe(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                callOriginalAttachInfoFromPrivacy(context, info);
                return null;
            }
        });
    }


    @Override
    public void callOriginalAttachInfoFromPrivacy(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
    }
}
