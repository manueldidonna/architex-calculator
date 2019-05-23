package com.manueldidonna.architex.mathevaluator.utils

import com.manueldidonna.architex.mathevaluator.DIVIDE_SYMBOL
import com.manueldidonna.architex.mathevaluator.MINUS_SYMBOL
import com.manueldidonna.architex.mathevaluator.MULTIPLY_SYMBOL
import com.manueldidonna.architex.mathevaluator.PLUS_SYMBOL

fun StringBuilder.replaceLast(newValue: Char) {
    deleteCharAt(lastIndex).append(newValue)
}

fun StringBuilder.checkLastIsOperator() = last().run {
    this == PLUS_SYMBOL || this == MINUS_SYMBOL || this == DIVIDE_SYMBOL || this == MULTIPLY_SYMBOL
}