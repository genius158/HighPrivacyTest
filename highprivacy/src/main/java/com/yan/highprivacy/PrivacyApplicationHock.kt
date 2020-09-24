package com.yan.highprivacyimport android.app.Applicationimport android.content.Context/** * @author Bevan (Contact me: https://github.com/genius158) * @since 2020/9/22 * PrivacyApplicationHock 由业务具体实现 * 该类为实现样例，继承原本的应用原本的application */class PrivacyApplicationHock : Application() {    init {        // 设置权限Activity class        PrivacyMgr.privacy.highPrivacyActivityClass = { PrivacyActivity::class.java }        // 不拦截的activity class 列表，比如我们要跳转的H5隐私权益界面        PrivacyMgr.privacy.interceptIgnoreActivityClass = { arrayOf() }    }    override fun onCreate() {        PrivacyMgr.appLife.onCreate(this) { super.onCreate() }    }    override fun attachBaseContext(base: Context) {        PrivacyMgr.appLife.onAttachContext(this, base) { super.attachBaseContext(base) }    }}