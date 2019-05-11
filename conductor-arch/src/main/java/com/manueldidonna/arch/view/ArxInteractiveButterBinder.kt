package com.manueldidonna.arch.view

import android.view.View
import com.manueldidonna.arch.state.ArxState
import io.reactivex.Observable

abstract class ArxInteractiveButterBinder<S : ArxState, E : Any>(view: View) : ArxButterBinder<S>(view),
    ArxInteractiveView<E> {
    private val subject = createSubject()

    final override fun getUserInteractionEvents(): Observable<E> {
        return subject
    }

    protected fun emit(event: E) {
        subject.onNext(event)
    }

}