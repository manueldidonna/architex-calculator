package com.manueldidonna.arch

import android.view.View
import com.bluelinelabs.conductor.Controller
import java.io.Serializable

fun <T> Controller.conductorLazy(initializer: () -> T): Lazy<T> = ConductorLifecycleAwareLazy(this, initializer)

private object UNINITIALIZED_VALUE

private class ConductorLifecycleAwareLazy<out T>(controller: Controller, initializer: () -> T) : Lazy<T>, Serializable {
    private var initializer: (() -> T)? = initializer
    @Volatile
    private var _value: Any? = UNINITIALIZED_VALUE
    // final field is required to enable safe publication of constructed instance
    private val lock = this

    init {
        controller.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                if (!isInitialized()) value
                controller.removeLifecycleListener(this)
            }
        })
    }

    override val value: T
        get() {
            val _v1 = _value
            if (_v1 !== UNINITIALIZED_VALUE) {
                @Suppress("UNCHECKED_CAST")
                return _v1 as T
            }

            return synchronized(lock) {
                val _v2 = _value
                if (_v2 !== UNINITIALIZED_VALUE) {
                    @Suppress("UNCHECKED_CAST") (_v2 as T)
                } else {
                    val typedValue = initializer!!()
                    _value = typedValue
                    initializer = null
                    typedValue
                }
            }
        }

    override fun isInitialized(): Boolean = _value !== UNINITIALIZED_VALUE

    override fun toString(): String = if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}