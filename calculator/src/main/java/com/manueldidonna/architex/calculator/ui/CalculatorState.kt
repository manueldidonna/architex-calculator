package com.manueldidonna.architex.calculator.ui

import com.manueldidonna.arch.state.ArxState

data class CalculatorState(
    val expression: String = "",
    val result: String = "",
    val errorOnResult: Boolean = false,
    val tapBackToDelete: Boolean = false
) : ArxState
