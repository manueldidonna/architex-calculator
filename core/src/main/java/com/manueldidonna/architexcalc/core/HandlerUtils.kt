package com.manueldidonna.architexcalc.core

import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.annotation.MainThread

@MainThread
fun getAsyncHandler(threadName: String): Handler = createLooper(threadName).createHandler(true)

fun createLooper(threadName: String): Looper = HandlerThread(threadName).apply { start() }.looper

fun Looper.createHandler(async: Boolean): Handler = when {
    !async -> Handler(this)
    Build.VERSION.SDK_INT >= 28 -> Handler.createAsync(this)
    Build.VERSION.SDK_INT >= 16 -> try {
        Handler::class.java.getDeclaredConstructor(
            Looper::class.java,
            Handler.Callback::class.java,
            Boolean::class.javaPrimitiveType
        ).newInstance(this, null, true)
    } catch (ignored: Throwable) {
        Handler(this)
    }
    else -> Handler(this)
}