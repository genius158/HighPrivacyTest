package com.yan.highprivacy

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 * 额外授权通知
 */
internal class ExtraAuthDispatcher {

    companion object {
        val dispatcher by lazy { ExtraAuthDispatcher() }
    }

    private val onDispatches = ArrayList<() -> Unit>()

    fun observe(onDispatch: () -> Unit) {
        onDispatches.add(onDispatch)
    }

    fun dispatchAuth() {
        for (dispatch in onDispatches) {
            dispatch.invoke()
        }
    }

}