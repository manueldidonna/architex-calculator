package com.manueldidonna.architexcalc.core

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.*

interface Interactor<in P> {
    val dispatcher: CoroutineDispatcher
    suspend operator fun invoke(param: P)
}

abstract class RxInteractor<P : Any, T> {
    private val subject: Subject<P> = BehaviorSubject.create()
    private val observable = subject.switchMap(::createObservable)

    operator fun invoke(params: P) = subject.onNext(params)

    protected abstract fun createObservable(params: P): Observable<T>

    fun observe(): Observable<T> = observable
}

fun <P> CoroutineScope.launchInteractor(interactor: Interactor<P>, param: P): Job {
    return launch(context = interactor.dispatcher, block = { interactor(param) })
}

fun CoroutineScope.launchInteractor(interactor: Interactor<Unit>): Job {
    return launch(context = interactor.dispatcher, block = { interactor(Unit) })
}

suspend fun <P> Interactor<P>.execute(param: P) = withContext(context = dispatcher) {
    invoke(param)
}