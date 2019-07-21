package com.manueldidonna.arch.view

import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

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

/**
 * An implementation of [ArxInteractiveView] with [PublishSubject]
 */
class ArxSubjectView<E : Any> : ArxInteractiveView<E> {
    private val subject = PublishSubject.create<E>().toSerialized()

    override fun emitEvent(event: E) {
        subject.onNext(event)
    }

    override fun getUserInteractionEvents(): Observable<E> {
        return subject
    }
}