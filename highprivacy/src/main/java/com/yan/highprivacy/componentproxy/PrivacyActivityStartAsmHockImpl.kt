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

    private var callStartActImpl = PrivacyCallActivityStartRealImpl(this)

    override fun callPrivacyActClass(className: String, intent: Intent?) = className

    override fun callOriginalPrivacyActStart(context: Context) {
    }

    override fun interceptOriginalActProcess(context: Context, actClazz: Class<*>): Boolean {
        if (PrivacyMgr.privacy.isAuth()) return false
        val className =
            callStartActImpl.callPrivacyActClass(
                actClazz.name,
                Intent(context, actClazz).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        return actClazz.name != className
    }

}