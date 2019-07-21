package com.manueldidonna.arch.statemanager

import com.manueldidonna.arch.state.ArxState
import io.reactivex.Observable

interface ReactiveArxStateManager<S : ArxState> : ArxStateManager<S> {
    fun observable(): Observable<S>
}
