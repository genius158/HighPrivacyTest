package com.yan.asmlocal;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/25
 */
public class AsmPrivacyClassPathConfig {

    /* ----------------------------------lib|--------------------------------- */
    static final String EXTRA_AUTH_DISPATCHER_CLASS_PATH = "com/yan/highprivacy/ExtraAuthDispatcher";
    static final String EXTRA_AUTH_DISPATCHER_METHOD_GET = "getDispatcher";
    static final String EXTRA_AUTH_DISPATCHER_METHOD_GET_DES = "()Lcom/yan/highprivacy/ExtraAuthDispatcher;";
    static final String EXTRA_AUTH_DISPATCHER_METHOD_OBSERVE = "observe";
    static final String EXTRA_AUTH_DISPATCHER_METHOD_OBSERVE_DES = "(Landroid/content/ContentProvider;Landroid/content/Context;Landroid/content/pm/ProviderInfo;)V";

    static final String PRIVACY_MGR_CLASS_PATH = "com/yan/highprivacy/PrivacyMgr";
    static final String PRIVACY_METHOD_GET_DES = "()Lcom/yan/highprivacy/Privacy;";
    static final String PRIVACY_CLASS_PATH = "com/yan/highprivacy/Privacy";
    static final String PRIVACY_METHOD_GET_PRIVACY = "getPrivacy";
    static final String PRIVACY_METHOD_IS_AUTH = "isAuth";
    static final String PRIVACY_METHOD_IS_AUTH_DES = "()Z";
    /* ----------------------------------|lib--------------------------------- */

    static final String ACTIVITY_CONTENT_CLASS_PATH = "android/app/Activity";
    static final String ACTIVITY_METHOD_ON_CREATE = "onCreate";
    static final String ACTIVITY_METHOD_FINISH = "finish";
    static final String ACTIVITY_METHOD_FINISH_DES = "()V";
    static final String ACTIVITY_METHOD_ON_CREATE_DES = "(Landroid/os/Bundle;)V";

    static final String PROVIDER_CONTENT_CLASS_PATH = "android/content/ContentProvider";
    static final String PROVIDER_METHOD_ATTACH_INFO = "attachInfo";
    static final String PROVIDER_METHOD_ATTACH_INFO_DES = "(Landroid/content/Context;Landroid/content/pm/ProviderInfo;)V";


    static final String SERVICE_CLASS_PATH = "android/app/Service";
    static final String SERVICE_METHOD_ATTACH_BASE_CONTEXT = "attachBaseContext";
    static final String SERVICE_METHOD_ATTACH_BASE_CONTEXT_DES = "(Landroid/content/Context;)V";
    static final String SERVICE_METHOD_START_COMMAND = "onStartCommand";
    static final String SERVICE_METHOD_START_COMMAND_DES = "(Landroid/content/Intent;II)I";
    static final String SERVICE_METHOD_STOP_SELF = "stopSelf";
    static final String SERVICE_METHOD_STOP_SELF_DES = "()V";


    static final String START_ASM_HOCK_CLASS = "com/yan/highprivacy/componentproxy/PrivacyActivityStartAsmHockImpl";
    static final String SERVICE_METHOD_GET_INS = "getIns";
    static final String SERVICE_METHOD_GET_INS_DES = "()Lcom/yan/highprivacy/componentproxy/PrivacyActivityStartAsmHockImpl;";

    static final String START_ACT_PROCESS_METHOD = "interceptOriginalActProcess";
    static final String START_ACT_PROCESS_METHOD_DES = "(Landroid/content/Context;Ljava/lang/Class;)Z";


    static final String BROADCAST_RECEIVE_METHOD_REPLACE = "onReceive";
    static final String BROADCAST_RECEIVE_METHOD_REPLACE_DES = "(Landroid/content/Context;Landroid/content/Intent;)V";
    static final String BROADCAST_RECEIVE_METHOD_GO_ASYNC = "goAsync";
    static final String BROADCAST_RECEIVE_METHOD_GO_ASYNC_DES = "()Landroid/content/BroadcastReceiver$PendingResult;";
    static final String BROADCAST_RECEIVE_RENDING_RESULT = "android/content/BroadcastReceiver$PendingResult";
    static final String BROADCAST_RECEIVE_METHOD_FINISH = "finish";
    static final String BROADCAST_RECEIVE_METHOD_FINISH_DES = "()V";


    static final String OBJECT_CLASS = "java/lang/Object";
    static final String OBJECT_METHOD_GET_CLASS = "getClass";
    static final String OBJECT_METHOD_GET_CLASS_DES = "()Ljava/lang/Class;";


}
