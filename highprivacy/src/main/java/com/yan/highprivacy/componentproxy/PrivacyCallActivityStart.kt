package com.yan.highprivacy.componentproxy

import android.content.Context
import android.content.Intent

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/25
 */
interface PrivacyCallActivityStart {
    fun callPrivacyActClass(
        className: String,
        intent: Intent?
    ): String

    fun callOriginalPrivacyActStart(context: Context)
}