package com.manueldidonna.arch.viewmodel

import com.bluelinelabs.conductor.Controller
import com.manueldidonna.arch.conductorLazy
import com.manueldidonna.arch.state.ArxState

// TODO: documentare i casi in cui è dichiarato un errore

/**
 * Return a restored state [S] or [newState]
 */
fun <S : ArxState> ArxViewModelOwner.restoredState(newState: S): S {
    // TODO: return a saved state if it exists
    return newState
}

inline fun <reified VM : ArxViewModel, S : ArxState> ArxViewModelOwner.create(
    newState: S,
    crossinline viewModelCreator: (state: S) -> VM
): VM = viewModelCreator(restoredState(newState)).also { viewModel ->
    viewModelStore.put(VM::class.java.name, viewModel)
}

/**
 * Return an existing ArxViewModel scoped to the passed [ArxViewModelOwner].
 */
inline fun <reified VM : ArxViewModel> ArxViewModelOwner.getArxViewModel(owner: ArxViewModelOwner = this): VM {
    return owner.viewModelStore.get(VM::class.java.name) as? VM
        ?: throw IllegalStateException("ArxViewModelStore doesn't contain <${VM::class.java.name}>")
}

inline fun <reified VM : ArxViewModel, S : ArxState, C> C.arxViewModel(
    newState: S,
    crossinline viewModelCreator: (state: S) -> VM
) where C : ArxViewModelOwner, C : Controller = conductorLazy {
    viewModelCreator(restoredState(newState)).also { viewModel ->
        viewModelStore.put(VM::class.java.name, viewModel)
    }
}

// TODO: delegate by existingArxViewModel()