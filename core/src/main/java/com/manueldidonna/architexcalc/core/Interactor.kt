package com.manueldidonna.architexcalc.core

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.*

interface Interactor<in P> {
    val dispatcher: CoroutineDispatcher
    suspend operator fun invoke(param: P)
}

abstract class RxInteractor<in P, T> : Interactor<P> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.Default

    private var disposable: Disposable? = null
    private val subject: BehaviorSubject<T> = BehaviorSubject.create()

    fun observe(): Flowable<T> = subject.toFlowable(BackpressureStrategy.LATEST)

    fun dispose(){
        disposable?.dispose()
    }

    final override suspend fun invoke(param: P) {
        disposable?.dispose()
        disposable = createObservable(param).subscribe(subject::onNext, subject::onError)
    }

    protected abstract fun createObservable(params: P): Flowable<T>
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