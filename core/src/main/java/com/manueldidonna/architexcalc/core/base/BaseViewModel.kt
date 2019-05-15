package com.manueldidonna.architexcalc.core.base

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.viewmodel.ArxStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseViewModel<S : ArxState>(initialState: S) : ArxStateViewModel<S>(initialState) {
    protected val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}