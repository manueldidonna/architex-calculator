package com.manueldidonna.architexcalc.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.manueldidonna.arch.controller.ReactiveArxController
import com.manueldidonna.arch.controller.observeEvents
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxView
import com.manueldidonna.arch.view.ArxViewOwner
import com.manueldidonna.arch.view.BaseArxViewOwner
import org.koin.core.KoinComponent
import org.koin.core.scope.Scope

@Suppress("LeakingThis")
abstract class BaseStatelessController(args: Bundle?) : ReactiveArxController(args), KoinComponent,
    ArxViewOwner by BaseArxViewOwner() {

    protected constructor() : this(null)

    protected val controllerScope: Scope by lazy { getKoin().createScope(controllerId) }

    @get:LayoutRes
    abstract val layoutRes: Int

    /**
     * The layout is inflated by default from [layoutRes] resource
     */
    final override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(layoutRes, container, false)
    }

    @CallSuper
    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        clearViews()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        controllerScope.close()
    }

    protected inline fun <V : ArxInteractiveView<E>, E : Any> V.onEvents(crossinline block: (E) -> Unit) = apply {
        observeEvents(this, block)
    }
}