package com.manueldidonna.architexcalc.main.numkeyboard

import com.manueldidonna.architex.mathevaluator.MathOperator

sealed class NumKeyboardEvent {
    object Cancel : NumKeyboardEvent()
    object Clear : NumKeyboardEvent()
    object Equal : NumKeyboardEvent()
    object DecimalSeparator : NumKeyboardEvent()
    data class Number(val value: String) : NumKeyboardEvent()
    data class Operator(val value: MathOperator) : NumKeyboardEvent()
}