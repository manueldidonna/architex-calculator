package com.manueldidonna.arch.controller

import com.manueldidonna.arch.ControllerDisposable
import com.manueldidonna.arch.view.ArxInteractiveView
import io.reactivex.disposables.Disposable

interface ReactiveController {
    val controllerDisposable: ControllerDisposable

    fun Disposable.disposeOnDetach(): Disposable = apply {
        controllerDisposable.untilDetachSubscriptions.add(this)
    }

    fun Disposable.disposeOnDestroyView(): Disposable = apply {
        controllerDisposable.untilDestroyViewSubscriptions.add(this)
    }
}

inline fun <E : Any> ReactiveController.observeEvents(view: ArxInteractiveView<E>, crossinline body: (E) -> Unit) {
    view.getUserInteractionEvents().subscribe { body(it) }.disposeOnDestroyView()
}
