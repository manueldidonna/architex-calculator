package com.manueldidonna.architexcalc.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.manueldidonna.arch.controller.StatefulArxController
import com.manueldidonna.arch.controller.observeEvents
import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.statemanager.ReactiveArxStateManager
import com.manueldidonna.arch.view.*
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.scope.Scope
import kotlin.reflect.KProperty1

abstract class BaseStatefulController<SM : ReactiveArxStateManager<S>, S : ArxState>(args: Bundle?) :
    StatefulArxController<SM, S>(args), ArxViewOwner by BaseArxViewOwner(), KoinComponent {

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

    protected fun <V : ArxBinderView<S>> V.subscribeToStateChanges() = apply {
        stateManager
            .observable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::dispatchNewState)
            .disposeOnDestroyView()
    }

    protected fun <V : ArxRenderView<S>> V.subscribeToStateChanges() = apply {
        stateManager
            .observable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::render)
            .disposeOnDestroyView()
    }

    private data class ArxTuple1<A>(val a: A)
    private data class ArxTuple2<A, B>(val a: A, val b: B)

    protected fun <A, S2, V : ArxRenderView<S2>> V.subscribeToStateChanges(
        prop1: KProperty1<S, A>,
        subscriber: (A) -> S2
    ) = apply {
        stateManager
            .observable()
            .map { ArxTuple1(prop1.get(it)) }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (a) -> this.render(subscriber(a)) }
            .disposeOnDestroyView()
    }

    protected fun <A, B, S2, V : ArxRenderView<S2>> V.subscribeToStateChanges(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        subscriber: (A, B) -> S2
    ) = apply {
        stateManager
            .observable()
            .map { ArxTuple2(prop1.get(it), prop2.get(it)) }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (a, b) -> this.render(subscriber(a, b)) }
            .disposeOnDestroyView()
    }
}