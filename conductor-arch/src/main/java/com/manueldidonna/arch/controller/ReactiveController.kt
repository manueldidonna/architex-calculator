package com.manueldidonna.arch.controller

import com.manueldidonna.arch.view.ArxInteractiveView
import io.reactivex.disposables.Disposable

interface ReactiveController {
    fun Disposable.disposeOnDetach(): Disposable
    fun Disposable.disposeOnDestroyView(): Disposable
}

inline fun <E : Any> ReactiveController.observeEvents(view: ArxInteractiveView<E>, crossinline body: (E) -> Unit) {
    view.getUserInteractionEvents().subscribe { body(it) }.disposeOnDestroyView()
}
