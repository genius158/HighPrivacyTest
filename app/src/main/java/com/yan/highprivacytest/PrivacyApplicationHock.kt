package com.yan.highprivacytestimport android.content.Contextimport com.yan.highprivacy.PrivacyMgr/** * @author Bevan (Contact me: https://github.com/genius158) * @since 2020/9/22 */class PrivacyApplicationHock : TestApp() {    init {        // 设置权限Activity class        PrivacyMgr.privacy.highPrivacyActivityClass = { PrivacyActivity::class.java }    }    /**     * 固定实现     */    override fun onCreate() {        PrivacyMgr.privacy.onCreate(this) { super.onCreate() }    }    /**     * 固定实现     */    override fun attachBaseContext(base: Context) {        PrivacyMgr.privacy.onAttachContext(this, base) { super.attachBaseContext(base) }    }}