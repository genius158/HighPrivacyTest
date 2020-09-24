package com.yan.highprivacyimport android.app.ActivityManagerimport android.app.Applicationimport android.content.Contextimport android.content.ContextWrapperimport android.os.Processimport kotlin.system.exitProcess/** * @author Bevan (Contact me: https://github.com/genius158) * @since  2020/9/22 */class PrivacyMgr(    override var highPrivacyActivityClass: (() -> Class<*>)? = null,    override var interceptIgnoreActivityClass: (() -> Array<Class<*>>)? = null) : Privacy, AppLife {    companion object {        private val privacyMgr by lazy { PrivacyMgr() }        @JvmStatic        val privacy: Privacy by lazy { privacyMgr }                @JvmStatic        val appLife: AppLife by lazy { privacyMgr }    }    private lateinit var appHock: Application    private lateinit var baseContext: Context    private val configSp by lazy {        baseContext.getSharedPreferences(            PrivacyApplicationHock::class.java.canonicalName,            Context.MODE_PRIVATE        )    }    private var hasAuth = false        get() {            if (!field) field = configSp.getBoolean("isAuth", false)            return field        }        set(value) {            field = value            val edit = configSp.edit()                .putBoolean("isAuth", value)            if (value) edit.apply() else edit.commit()        }    override fun isAuth(): Boolean = hasAuth    /**     * 实现延后调用onAttachContext     */    private var onAttachContextHock: (() -> Unit)? = null    override fun onAttachContext(        app: Application,        context: Context,        onAttachContextHock: () -> Unit    ) {        this.appHock = app        this.baseContext = context        if (hasAuth) {            //授权过了，直接回调super.onAttachContext            onAttachContextHock.invoke()        } else {            this.onAttachContextHock = onAttachContextHock            // 没有授权过，我们要启动我们自己的授权界面，            // 这里hock Instrumentation 拦截 activity            InstrumentationProxy.hookInstrumentation(context)            /*             * 启动过程中，会调用到application里的方法，             * 因为这里还没掉用原本的attachContext方法，             * mBase 会为空，需要强行设置             */            fieldBase.set(app, context)        }    }    private val fieldBase by lazy {        ContextWrapper::class.java.getDeclaredField("mBase")            .also { fb -> fb.isAccessible = true }    }    /**     *  实现延后调用onCreateHock     */    private var onCreateHock: (() -> Unit)? = null    override fun onCreate(        appHock: Application,        onCreateHock: () -> Unit    ) {        this.appHock = appHock        if (hasAuth) {            onCreateHock.invoke()        } else {            this.onCreateHock = onCreateHock        }    }    private fun authOk() {        hasAuth = true        fieldBase.set(appHock, null)        onAttachContextHock?.invoke()        ExtraAuthDispatcher.dispatcher.dispatchAuth()        onCreateHock?.invoke()    }    /**     * 授权成功分发     */    override fun dispatchAuth() {        authOk()        // 授权流程走完，恢复原本的instrumentation，及任务        InstrumentationProxy.instrumentationProxy?.recovery()    }    override fun dispatchUnAuth() {        hasAuth = false        restartSelf()        killAppProcess()    }    private fun killAppProcess() {        val activityManager =            appHock.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager        val mList =            activityManager.runningAppProcesses        for (runningAppProcessInfo in mList) {            if (runningAppProcessInfo.pid != Process.myPid()) {                Process.killProcess(runningAppProcessInfo.pid)            }        }        Process.killProcess(Process.myPid())        exitProcess(0)    }    private fun restartSelf() {        appHock.packageManager?.getLaunchIntentForPackage(appHock.packageName)            ?.let { launchIntent -> appHock.startActivity(launchIntent) }    }}