package com.manueldidonna.arch.viewmodel

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.state.ArxStateStore
import com.manueldidonna.arch.state.RealArxStateStore
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ArxStateViewModel<S : ArxState>(
    initialState: S,
    private val stateStore: ArxStateStore<S> = RealArxStateStore(initialState)
) : ArxViewModel() {
    private val disposables = CompositeDisposable()

    internal val state: S get() = stateStore.state

    /**
     * Access the current ViewModel state. Takes a block of code that will be run after all current pending state
     * updates are processed. The `this` inside of the block is the state.
     */
    protected fun withState(block: (state: S) -> Unit) {
        stateStore.get(block)
    }

    /**
     * Call this to mutate the current state.
     * A few important notes about the state reducer.
     * 1) It will not be called synchronously or on the same thread. This is for performance and accuracy reasons.
     * 2) Similar to the execute lambda above, the current state is the state receiver so the `count` in `count + 1` is actually the count
     *    property of the state at the time that the lambda is called.
     */
    protected fun setState(reducer: S.() -> S) {
        stateStore.set(reducer)
    }

    /**
     * For ViewModels that want to subscribe to itself.
     */
    protected fun subscribe(
        scheduler: Scheduler = AndroidSchedulers.mainThread(),
        subscriber: (S) -> Unit
    ) = stateStore.observable.observeOn(scheduler)
        .subscribe(subscriber)
        .disposeOnClear()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    /** RxJava Utilities */
    protected fun Disposable.disposeOnClear(): Disposable {
        disposables.add(this)
        return this
    }
}