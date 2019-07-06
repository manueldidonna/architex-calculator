@file:Suppress("unused")

package com.manueldidonna.arch.view

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface ArxView

interface ArxRenderView<S> : ArxView {
    fun render(state: S)
}

/**
 * @see ArxSubjectView for a sample implementation
 */
interface ArxInteractiveView<E : Any> : ArxView {
    fun getUserInteractionEvents(): Observable<E>
    fun emitEvent(event: E)
}

fun <E : Any> ArxInteractiveView<E>.createSubject(): Subject<E> {
    return PublishSubject.create<E>().toSerialized()
}