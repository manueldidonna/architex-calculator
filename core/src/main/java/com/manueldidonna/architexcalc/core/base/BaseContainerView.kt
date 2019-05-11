package com.manueldidonna.architexcalc.core.base

import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxViewBinder
import com.manueldidonna.arch.view.createSubject
import io.reactivex.Observable

@Suppress("LeakingThis")
abstract class BaseContainerView<S : ArxState, E : Any>(view: View) : ArxViewBinder<S>(view),
    ArxInteractiveView<E> {
    protected val unbinder: Unbinder = ButterKnife.bind(this, view)

    private val subject = createSubject()

    final override fun getUserInteractionEvents(): Observable<E> {
        return subject
    }

    protected fun View.emitOnClick(event: E) {
        setOnClickListener { subject.onNext(event) }
    }

    protected fun emit(event: E) {
        subject.onNext(event)
    }
}