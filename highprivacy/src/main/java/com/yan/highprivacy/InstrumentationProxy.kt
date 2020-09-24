package com.yan.highprivacyimport android.annotation.SuppressLintimport android.app.Activityimport android.app.Instrumentationimport android.content.Contextimport android.content.Intentimport android.os.Bundleimport android.os.IBinderimport androidx.annotation.Keepimport java.lang.reflect.Fieldimport java.lang.reflect.InvocationTargetException/** * @author Bevan (Contact me: https://github.com/genius158) * @since 2020/9/23 */internal class InstrumentationProxy(    private val baseContext: Context,    private val originalInstrumentation: Instrumentation) : Instrumentation() {    companion object {        var instrumentationProxy: InstrumentationProxy? = null        private var instrumentationField: Field? = null        private var activityThread: Any? = null        private var instrumentationOriginal: Instrumentation? = null        @Throws(Exception::class)        fun hookInstrumentation(            context: Context,            instrumentation: Instrumentation? = null        ) {            if (activityThread == null) {                val contextImpClass = Class.forName("android.app.ContextImpl")                val mainThreadField = contextImpClass.getDeclaredField("mMainThread")                mainThreadField.isAccessible = true                activityThread = mainThreadField.get(context)            }            if (instrumentationField == null) {                val activityThreadClass = Class.forName("android.app.ActivityThread")                instrumentationField = activityThreadClass.getDeclaredField("mInstrumentation")                instrumentationField?.isAccessible = true            }            if (instrumentationOriginal == null) {                instrumentationOriginal =                    instrumentationField?.get(activityThread) as Instrumentation            }            val instField = instrumentationField            val instOriginal = instrumentationOriginal            requireNotNull(instField) { "Something went wrong" }            requireNotNull(instOriginal) { "Something went wrong" }            if (instrumentation == null) {                if (instrumentationProxy == null) {                    instOriginal.let { instrumentationProxy = InstrumentationProxy(context, it) }                }                instField.set(activityThread, instrumentationProxy)            } else {                instField.set(activityThread, instrumentation)            }        }    }    override fun getContext() = baseContext    private fun providerActivityClass(): Class<*> {        val clazz = PrivacyMgr.privacy.highPrivacyActivityClass?.invoke()        requireNotNull(clazz) { "privacyHandle must be set in PrivacyMgr" }        return clazz    }    /**     * @Keep 方便混淆设置     */    @SuppressLint("DiscouragedPrivateApi")    @Keep    fun execStartActivity(        who: Context?,        contextThread: IBinder?,        token: IBinder?,        target: Activity?,        intent: Intent?,        requestCode: Int,        options: Bundle?    ): ActivityResult? {        originalIntent = intent        var fIntent = intent        if (!PrivacyMgr.privacy.isAuth) {            fIntent = intent?.clone() as Intent            fIntent.setClass(context, providerActivityClass())        }        try {            val startActivityMethod =                Instrumentation::class.java.getDeclaredMethod(                    "execStartActivity",                    Context::class.java,                    IBinder::class.java,                    IBinder::class.java,                    Activity::class.java,                    Intent::class.java,                    Int::class.javaPrimitiveType,                    Bundle::class.java                )            startActivityMethod.isAccessible = true            return startActivityMethod.invoke(                originalInstrumentation,                who,                contextThread,                token,                target,                fIntent,                requestCode,                options            ) as ActivityResult        } catch (e: NoSuchMethodException) {            e.printStackTrace()        } catch (e: IllegalAccessException) {            e.printStackTrace()        } catch (e: InvocationTargetException) {            e.printStackTrace()        }        return null    }    override fun newActivity(        cl: ClassLoader?,        className: String?,        intent: Intent?    ): Activity {        if (!PrivacyMgr.privacy.isAuth) {            originalIntent = intent            /*             * 没有授权，我们把要启动的界面，替换成我们的权限界面             */            val fIntent = intent?.clone() as Intent            fIntent.setClass(context, providerActivityClass())            return originalInstrumentation.newActivity(cl, providerActivityClass().name, fIntent)        }        return originalInstrumentation.newActivity(cl, className, intent)    }    /**     * 上次发起的activity启动 Intent     * 存在多次调用始终使用最新的，     * 没授权之前所有的调用，其实都没有意义     */    private var originalIntent: Intent? = null    fun recovery() {        // 授权流程走完，还原原本的instrumentation        hookInstrumentation(context, instrumentationOriginal)        // 还原原本的执行任务        context.startActivity(            Intent().setComponent(originalIntent?.component)                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)        )        release()    }    private fun release() {        instrumentationProxy = null        instrumentationField = null        activityThread = null        instrumentationOriginal = null    }}