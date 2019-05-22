package com.manueldidonna.architex.mathevaluator

fun StringBuilder.replaceLast(newValue: Char) {
    deleteCharAt(lastIndex).append(newValue)
}

fun StringBuilder.checkLastIsOperator() = last().run {
    this == '-' || this == '+' || this == MathOperator.DIVIDE.value || this == MathOperator.MULTIPLY.value
}