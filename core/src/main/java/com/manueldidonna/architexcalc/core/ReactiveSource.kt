package com.manueldidonna.architexcalc.core

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

interface ReactiveSource<P, T> {
    fun observe(): Flowable<T>
    fun create(params: P): ReactiveSource<P, T>
}

abstract class SubjectSource<P, T> : ReactiveSource<P, T> {
    private var disposable: Disposable? = null
    private val subject: BehaviorSubject<T> = BehaviorSubject.create()

    override fun observe(): Flowable<T> = subject.toFlowable(BackpressureStrategy.LATEST)

    override fun create(params: P): ReactiveSource<P, T> {
        disposable?.dispose()
        disposable = createObservable(params).subscribe(subject::onNext, subject::onError)
        return this
    }

    protected abstract fun createObservable(params: P): Flowable<T>
}