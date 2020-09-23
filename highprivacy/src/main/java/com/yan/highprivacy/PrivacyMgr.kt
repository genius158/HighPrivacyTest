package com.yan.highprivacyimport android.app.Applicationimport android.content.Contextimport android.content.ContextWrapper/** * @author Bevan (Contact me: https://github.com/genius158) * @since  2020/9/22 */class PrivacyMgr(override var privacyHandle: PrivacyHandle?) : Privacy {    companion object {        @JvmStatic        val privacy: Privacy by lazy { PrivacyMgr(null) }    }    private lateinit var appHock: Application    private lateinit var baseContext: Context    private val configSp by lazy {        baseContext.getSharedPreferences(            PrivacyApplicationHock::class.java.canonicalName,            Context.MODE_PRIVATE        )    }    override var isAuth = false        get() {            if (!field) field = configSp.getBoolean("isAuth", false)            return field        }        set(value) {            field = value            configSp.edit()                .putBoolean("isAuth", value)                .apply()        }    /**     * 实现延后调用onAttachContext     */    private var onAttachContextHock: (() -> Unit)? = null    override fun onAttachContext(        app: Application,        context: Context,        onAttachContextHock: () -> Unit    ) {        this.appHock = app        this.baseContext = context        if (isAuth) {            //授权过了，直接回调super.onAttachContext            onAttachContextHock.invoke()        } else {            this.onAttachContextHock = onAttachContextHock            // 没有授权过，我们要启动我们自己的授权界面，            // 这里hock Instrumentation 拦截 activity            InstrumentationProxy.hookInstrumentation(context)            /*             * 启动过程中，会调用到application里的方法，             * 因为这里还没掉用原本的attachContext方法，             * mBase 会为空，需要强行设置             */            val fieldBase = ContextWrapper::class.java.getDeclaredField("mBase")            fieldBase.isAccessible = true            fieldBase.set(app, context)        }    }    /**     *  实现延后调用onCreateHock     */    private var onCreateHock: (() -> Unit)? = null    override fun onCreate(        appHock: Application,        onCreateHock: () -> Unit    ) {        this.appHock = appHock        if (isAuth) {            runCatching(onCreateHock)        } else {            this.onCreateHock = onCreateHock        }    }    private fun authOk() {        isAuth = true        onAttachContextHock?.let { runCatching(it) }        onCreateHock?.invoke()    }    /**     * 授权成功分发     */    override fun dispatchAuth() {        authOk()        // 授权流程走完，恢复原本的instrumentation，及任务        InstrumentationProxy.proxy?.recovery()    }}