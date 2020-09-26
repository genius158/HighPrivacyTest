package com.yan.highprivacy

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/23
 */
interface Privacy {

    fun dispatchAuth()

    /**
     * 取消授权，重启，杀死所有进程
     */
    fun dispatchUnAuth()

    fun isAuth(): Boolean

    /**
     * 拦截打开任意activity（除了[activityClassWhiteList]类型）意图
     * 定向到 本类型 activity
     */
    var highPrivacyActivityClass: Class<*>?

    /**
     * see [highPrivacyActivityClass]
     */
    var activityClassWhiteList: Array<Class<*>>?

}