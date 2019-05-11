package com.manueldidonna.architexcalc.main

import com.manueldidonna.arch.state.ArxState

data class MainState(
    val expression: String = "",
    val result: String = "",
    val errorOnResult: Boolean = false
) : ArxState
