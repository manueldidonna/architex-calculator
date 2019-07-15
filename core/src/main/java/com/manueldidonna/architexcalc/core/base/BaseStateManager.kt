package com.manueldidonna.architexcalc.core.base

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.state.RealArxStateStore
import com.manueldidonna.arch.statemanager.ArxStateManager
import com.manueldidonna.arch.statemanager.ReactiveArxStateManager
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseStateManager<S : ArxState>(initialState: S) : ReactiveArxStateManager<S> {
    private val stateStore = RealArxStateStore(initialState)

    protected val stateManagerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override val state: S get() = stateStore.state

    override fun observable(): Observable<S> = stateStore.observable

    protected fun withState(block: (state: S) -> Unit) {
        stateStore.get(block)
    }

    protected fun setState(reducer: S.() -> S) {
        stateStore.set(reducer)
    }

    override fun onCleared() {
        stateManagerScope.cancel()
    }
}