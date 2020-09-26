package com.yan.highprivacy.componentproxy

import android.content.Context

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/26
 */
interface PrivacyActivityStartAsmHock {
    fun interceptOriginalActProcess(context: Context, actClazz: Class<*>): Boolean
}