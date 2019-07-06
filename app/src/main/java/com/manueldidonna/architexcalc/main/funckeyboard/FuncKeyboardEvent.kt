package com.manueldidonna.architexcalc.main.funckeyboard

sealed class FuncKeyboardEvent {
    data class Func(val name: String) : FuncKeyboardEvent()
}