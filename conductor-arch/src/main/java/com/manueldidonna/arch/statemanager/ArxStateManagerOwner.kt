package com.manueldidonna.arch.statemanager

import com.manueldidonna.arch.state.ArxState

interface ArxStateManagerOwner<SM : ArxStateManager<S>, S : ArxState> {
    val stateManager: SM
}

inline fun <SM : ArxStateManager<S>, S : ArxState, C> ArxStateManagerOwner<SM, S>.withState(block: (S) -> C): C {
    return block(this.stateManager.state)
}
