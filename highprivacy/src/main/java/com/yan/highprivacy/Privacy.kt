package com.yan.highprivacy

import android.app.Application
import android.content.Context

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/23
 */
interface Privacy {
    fun onAttachContext(
        app: Application,
        context: Context,
        onAttachContextHock: () -> Unit
    )

    fun onCreate(
        appHock: Application,
        onCreateHock: () -> Unit
    )

    fun dispatchAuth()

    var privacyHandle: PrivacyHandle?

    var isAuth: Boolean
}