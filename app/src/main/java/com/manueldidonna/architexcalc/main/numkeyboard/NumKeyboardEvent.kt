package com.manueldidonna.architexcalc.main.numkeyboard

sealed class NumKeyboardEvent {
    object Cancel : NumKeyboardEvent()
    object Clear : NumKeyboardEvent()
    object Equal : NumKeyboardEvent()
    data class Number(val value: String) : NumKeyboardEvent()
    data class Operator(val value: Char) : NumKeyboardEvent()
}