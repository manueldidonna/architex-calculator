package com.manueldidonna.arch.state

import com.manueldidonna.arch.viewmodel.ArxStateViewModel

fun <A: ArxStateViewModel<B>, B: ArxState, C> withState(vm: A, block: (B) -> C)= block(vm.state)