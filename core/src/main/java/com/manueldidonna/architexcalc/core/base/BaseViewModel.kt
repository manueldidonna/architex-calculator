package com.manueldidonna.architexcalc.core.base

import androidx.annotation.CallSuper
import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.viewmodel.ArxStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseViewModel<S : ArxState>(initialState: S) : ArxStateViewModel<S>(initialState){
    protected val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}