package com.yan.highprivacy.componentproxy

import android.content.Context
import android.content.Intent
import com.yan.highprivacy.PrivacyMgr

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/25
 */
internal class PrivacyCallActivityStartRealImpl(private val proxy: PrivacyCallActivityStart) :
    PrivacyCallActivityStart {

    companion object {
        var actStarter: PrivacyCallActivityStart? = null
    }

    init {
        actStarter = this
    }

    /**
     * 原本启动的 Intent
     */
    private var originalIntent: Intent? = null

    private val interceptIgnoreActivityClass by lazy {
        PrivacyMgr.privacy.activityClassWhiteList?.map { clazz -> clazz.name }
            ?: arrayListOf()
    }

    /**
     * 白名单
     */
    private fun isActivityClassInWhiteList(className: String?): Boolean {
        if (PrivacyMgr.privacy.highPrivacyActivityClass?.name == className) return true
        return interceptIgnoreActivityClass.contains(className)
    }

    override fun callPrivacyActClass(
        className: String,
        intent: Intent?
    ): String {
        return if (!PrivacyMgr.privacy.isAuth() && !isActivityClassInWhiteList(className)) {
            originalIntent = intent?.clone() as? Intent
            val tmpClassName = PrivacyMgr.privacy.highPrivacyActivityClass?.name
            requireNotNull(tmpClassName) { "highPrivacyActivityClass must be set in Privacy" }
            tmpClassName
        } else {
            className
        }
    }

    private fun launchIntent(context: Context): Intent? {
        return context.packageManager?.getLaunchIntentForPackage(context.packageName)
    }

    override fun callOriginalPrivacyActStart(context: Context) {
        proxy.callOriginalPrivacyActStart(context)

        val intent = originalIntent ?: launchIntent(context)
        intent ?: return

        // 还原原本的执行任务
        context.startActivity(
            Intent().setComponent(intent.component).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
