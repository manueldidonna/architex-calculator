package com.manueldidonna.arch.state

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface ArxStateStore<S : Any> : Disposable {
    val state: S
    fun get(block: (S) -> Unit)
    fun set(stateReducer: S.() -> S)
    val observable: Observable<S>
}