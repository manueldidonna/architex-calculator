package com.manueldidonna.architexcalc.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.manueldidonna.arch.ControllerDisposable
import com.manueldidonna.arch.controller.ReactiveController
import com.manueldidonna.arch.controller.StatelessArxController
import com.manueldidonna.arch.view.ArxView
import com.manueldidonna.arch.view.ArxViewOwner

@Suppress("LeakingThis")
abstract class BaseStatelessController : StatelessArxController, ReactiveController, ArxViewOwner {

    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    final override val controllerDisposable: ControllerDisposable = ControllerDisposable()

    init {
        controllerDisposable.bindToControllerLifecycle(this)
    }

    @get:LayoutRes
    abstract val layoutRes: Int

    /**
     * The layout is inflated by default from [layoutRes] resource
     */
    final override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(layoutRes, container, false)
    }

    private val views: MutableList<ArxView> = mutableListOf()
    override fun <V : ArxView> addView(view: V): V = view.also { views.add(it) }

    override fun clearViews() {
        views.clear()
    }

    @CallSuper
    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        clearViews()
    }
}