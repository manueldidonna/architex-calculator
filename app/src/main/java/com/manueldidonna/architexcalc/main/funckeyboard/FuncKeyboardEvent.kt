package com.manueldidonna.architexcalc.main.funckeyboard

import com.manueldidonna.architex.mathevaluator.MathFunction
import com.manueldidonna.architex.mathevaluator.MathBracket
import com.manueldidonna.architex.mathevaluator.MathConstant

sealed class FuncKeyboardEvent {
    data class AppendFunction(val func: MathFunction) : FuncKeyboardEvent()
    data class AppendBracket(val bracket: MathBracket) : FuncKeyboardEvent()
    data class AppendConstant(val constant: MathConstant) : FuncKeyboardEvent()
    object AppendExp : FuncKeyboardEvent()
    object AppendScientificNotation : FuncKeyboardEvent()
}