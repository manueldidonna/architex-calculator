package com.manueldidonna.arch.controller

import android.view.View
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxView

@Suppress("MemberVisibilityCanBePrivate")
abstract class ArxViewController<V : ArxView> : ArxRxController() {

    private var controllerView: V? = null

    abstract fun onCreateControllerView(layoutView: View): V

    final override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        controllerView = onCreateControllerView(view).also { controllerView ->
            onControllerViewCreated(view, controllerView)
        }
    }

    open fun onControllerViewCreated(layoutView: View, controllerView: V) {}

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        controllerView = null
    }

    protected fun <E : Any> ArxInteractiveView<E>.subscribeUserInteractionEvents(
        subscriber: (E) -> Unit
    ) = getUserInteractionEvents().subscribe(subscriber).disposeOnDestroyView()

}