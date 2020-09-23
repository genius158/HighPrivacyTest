package com.yan.highprivacytest

import android.app.Activity
import android.app.AlertDialog
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.yan.highprivacy.PrivacyMgr.Companion.privacy

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/23
 */
class PrivacyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AlertDialog.Builder(this)
            .setPositiveButton("授权") { dialog, which ->
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                privacy.dispatchAuth()
                finish()
                nextActivityFadeIn()
            }
            .setNegativeButton("撤销") { dialog, which -> finish() }
            .show()
    }

    /**
     * 原本界面淡入
     */
    private fun nextActivityFadeIn() {
        application.registerActivityLifecycleCallbacks(object :
            ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle?
            ) {
                application.unregisterActivityLifecycleCallbacks(this)
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivityDestroyed(activity: Activity) {}
            override fun onActivitySaveInstanceState(
                activity: Activity,
                outState: Bundle
            ) {
            }
        })
    }
}