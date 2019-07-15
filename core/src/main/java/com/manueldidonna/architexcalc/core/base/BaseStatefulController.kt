package com.manueldidonna.architexcalc.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.manueldidonna.arch.ControllerDisposable
import com.manueldidonna.arch.controller.ReactiveController
import com.manueldidonna.arch.controller.StatefulArxController
import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.statemanager.ReactiveArxStateManager
import com.manueldidonna.arch.view.ArxRenderView
import com.manueldidonna.arch.view.ArxView
import com.manueldidonna.arch.view.ArxViewOwner
import io.reactivex.android.schedulers.AndroidSchedulers

@Suppress("LeakingThis")
abstract class BaseStatefulController<SM : ReactiveArxStateManager<S>, S : ArxState> : StatefulArxController<SM, S>,
    ReactiveController, ArxViewOwner {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    @get:LayoutRes
    abstract val layoutRes: Int

    final override val controllerDisposable = ControllerDisposable()

    init {
        controllerDisposable.bindToControllerLifecycle(this)
    }

    /**
     * The layout is inflated by default from [layoutRes] resource
     */
    final override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(layoutRes, container, false)
    }

    private val views: MutableList<ArxView> = mutableListOf()
    override fun <V : ArxView> addView(view: V): V = view.also { views.add(it) }

    override fun clearViews() {
        renderLoopers.clear()
        views.clear()
    }

    private val renderLoopers: MutableList<(S) -> Unit> = mutableListOf()
    protected fun <S2> renderState(view: ArxRenderView<S2>, body: (S) -> S2) {
        renderLoopers.add { state ->
            view.render(body(state))
        }
    }

    @CallSuper
    override fun onViewCreated(view: View) {
        stateManager
            .observable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                ArrayList(renderLoopers).forEach { render ->
                    render(state)
                }
            }.disposeOnDestroyView()
    }

    @CallSuper
    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        clearViews()
    }
}