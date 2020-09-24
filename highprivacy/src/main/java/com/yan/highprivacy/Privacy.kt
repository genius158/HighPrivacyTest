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

  /**
   * 拦截打开任意activity（除了[highPrivacyActivityClass]类型）意图
   * 定向到 本类型 activity
   */
  var highPrivacyActivityClass: (() -> Class<*>)?

  /**
   * see [highPrivacyActivityClass]
   */
  var interceptIgnoreActivityClass: (() -> Array<Class<*>>)?

  var isAuth: Boolean
}