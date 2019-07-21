package com.manueldidonna.arch.statemanager

import androidx.annotation.MainThread
import com.manueldidonna.arch.state.ArxState

interface ArxStateManager<S : ArxState> {
    val state: S

    @MainThread
    fun onCleared() = Unit
}

inline fun <SM : ArxStateManager<S>, S : ArxState, C> withState(sm: SM, block: (S) -> C) = block(sm.state)