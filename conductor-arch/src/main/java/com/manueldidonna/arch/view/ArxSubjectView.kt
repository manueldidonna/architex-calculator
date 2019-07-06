package com.manueldidonna.arch.view

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

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