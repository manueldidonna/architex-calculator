package com.manueldidonna.architexcalc.core.base

import androidx.annotation.CallSuper
import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.state.RealArxStateStore
import com.manueldidonna.arch.statemanager.ReactiveArxStateManager
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseStateManager<S : ArxState>(initialState: S) : ReactiveArxStateManager<S> {
    private val stateStore = RealArxStateStore(initialState)
    private val disposables = CompositeDisposable()

    protected val stateManagerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    final override val state: S get() = stateStore.state

    final override fun observable(): Observable<S> = stateStore.observable

    protected fun withState(block: (state: S) -> Unit) {
        stateStore.get(block)
    }

    protected fun setState(reducer: S.() -> S) {
        stateStore.set(reducer)
    }

    @CallSuper
    override fun onCleared() {
        stateManagerScope.cancel()
        disposables.clear()
    }

    /** RxJava Utilities */
    protected fun Disposable.disposeOnClear(): Disposable {
        disposables.add(this)
        return this
    }

    protected fun <T> Flowable<T>.autoSubscribe(onNext: (T) -> Unit): Disposable {
        return subscribe(onNext).disposeOnClear()
    }

    protected fun <T> Observable<T>.autoSubscribe(onNext: (T) -> Unit): Disposable {
        return subscribe(onNext).disposeOnClear()
    }
}