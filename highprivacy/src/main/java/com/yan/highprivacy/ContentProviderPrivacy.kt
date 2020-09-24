package com.yan.highprivacy

import android.content.ContentProvider
import android.content.Context
import android.content.pm.ProviderInfo

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 */
abstract class ContentProviderPrivacy : ContentProvider() {

    override fun attachInfo(context: Context?, info: ProviderInfo?) {
        if (PrivacyMgr.privacy.isAuth()) {
            super.attachInfo(context, info)
        } else {
            ExtraAuthDispatcher.dispatcher.observe {
                super.attachInfo(context, info)
            }
        }
    }
}