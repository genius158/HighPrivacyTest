package com.yan.highprivacy.componentproxy

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AppComponentFactory
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/25
 * startActivity 拦截AppComponentFactory 实现 api 28
 */
@SuppressLint("RestrictedApi")
@RequiresApi(api = Build.VERSION_CODES.P)
class PrivacyActivityStartAppComponentImpl : AppComponentFactory(),
    PrivacyCallActivityStart {

    @Throws(
        ClassNotFoundException::class,
        IllegalAccessException::class,
        InstantiationException::class
    )
    override fun instantiateActivity(
        cl: ClassLoader,
        className: String,
        intent: Intent?
    ): Activity {
        var fClassName = className
        intent?.getPackage()?.let { packageName ->
            fClassName = callStartActImpl.callPrivacyActClass(className, intent)
            intent.component = ComponentName(packageName, fClassName)
        }

        return super.instantiateActivity(cl, fClassName, intent)
    }


    private var callStartActImpl = PrivacyCallActivityStartRealImpl(this)

    override fun callPrivacyActClass(className: String, intent: Intent?) = className


    override fun callOriginalPrivacyActStart(context: Context) {
    }
}