package com.yan.highprivacy

import android.content.ContentProvider
import android.content.Context
import android.content.pm.ProviderInfo

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 * 额外授权通知
 */
class ExtraAuthDispatcher {

    companion object {
        @JvmStatic
        val dispatcher by lazy { ExtraAuthDispatcher() }
    }

    private val onDispatches = ArrayList<() -> Unit>()

    fun observe(onDispatch: () -> Unit) {
        onDispatches.add(onDispatch)
    }

    fun observe(provider: ContentProvider, context: Context, info: ProviderInfo) {
        observe { provider.attachInfo(context, info) }
    }


    fun dispatchAuth() {
        for (dispatch in onDispatches) {
            dispatch.invoke()
        }
    }

}