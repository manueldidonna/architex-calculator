package com.manueldidonna.arch.view

import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.manueldidonna.arch.state.ArxState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

@Suppress("LeakingThis")
abstract class ArxSubjectView<E : Any>(view: View) : ArxInteractiveView<E>{
    protected val unbinder: Unbinder = ButterKnife.bind(this, view)

    private val subject = PublishSubject.create<E>().toSerialized()

    final override fun getUserInteractionEvents(): Observable<E> {
        return subject
    }

    protected fun emit(event: E) {
        subject.onNext(event)
    }

}