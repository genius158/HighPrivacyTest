package com.yan.highprivacy.componentproxy

import android.content.Context
import android.content.Intent
import com.yan.highprivacy.PrivacyMgr

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/25
 *
 * startActivity 拦截，插桩在activity onCreate的实现
 */
class PrivacyActivityStartAsmHockImpl : PrivacyActivityStartAsmHock, PrivacyCallActivityStart {
  companion object {
    @JvmStatic
    val ins by lazy { PrivacyActivityStartAsmHockImpl() }
  }

  private var callStartActImpl: PrivacyCallActivityStart? = null

  init {
    if (!PrivacyMgr.privacyMgr.isStartActWithInstrument) {
      callStartActImpl = PrivacyCallActivityStartRealImpl(this)
    }
  }

  override fun callPrivacyActClass(
    className: String,
    intent: Intent?
  ) = className

  override fun callOriginalPrivacyActStart(context: Context) {
  }

  override fun interceptOriginalActProcess(
    context: Context,
    actClazz: Class<*>
  ): Boolean {
    val callStart = callStartActImpl ?: return false

    if (PrivacyMgr.privacy.isAuth()) return false
    val className =
      callStart.callPrivacyActClass(
          actClazz.name,
          Intent(context, actClazz).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      )
    val isIntercept = actClazz.name != className
    if (isIntercept) {
      val privacyClass = PrivacyMgr.privacy.highPrivacyActivityClass
      requireNotNull(privacyClass) { "highPrivacyActivityClass must be set in PrivacyMgr" }
      context.startActivity(
          Intent().setClass(context, privacyClass)
              .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      )
    }
    return isIntercept
  }

}