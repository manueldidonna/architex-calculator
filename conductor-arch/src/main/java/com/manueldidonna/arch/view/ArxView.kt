@file:Suppress("unused")

package com.manueldidonna.arch.view

import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface ArxView

interface ArxRenderView<S> : ArxView {
    fun render(state: S)
}

/**
 * @see ArxStateBinderView for a sample implementation
 */
interface ArxBinderView<S> : ArxView {
    fun dispatchNewState(state: S)
    fun <A : Any> bind(prop: (S) -> A, action: (A) -> Unit)
}

/**
 * @see ArxSubjectView for a sample implementation
 */
interface ArxInteractiveView<E : Any> : ArxView {
    fun getUserInteractionEvents(): Observable<E>
    fun emitEvent(event: E)
}

fun <E : Any> ArxInteractiveView<E>.emitOnClick(view: View, event: E) {
    view.setOnClickListener { emitEvent(event) }
}

fun <E : Any> ArxInteractiveView<E>.emitOnLongClick(view: View, event: E) {
    view.setOnLongClickListener { emitEvent(event); true }
}

fun <E : Any> ArxInteractiveView<E>.createSubject(): Subject<E> {
    return PublishSubject.create<E>().toSerialized()
}