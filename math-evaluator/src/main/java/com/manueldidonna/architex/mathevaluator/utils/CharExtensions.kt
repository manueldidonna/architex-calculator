package com.manueldidonna.architex.mathevaluator.utils

import com.manueldidonna.architex.mathevaluator.ALTERNATIVE_DIVIDE_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.ALTERNATIVE_MINUS_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.ALTERNATIVE_MULTIPLY_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.DIVIDE_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.MINUS_SYMBOL_CHAR
import com.manueldidonna.architex.mathevaluator.MULTIPLY_SYMBOL_CHAR

internal fun Char.isMinusSymbol() = this == MINUS_SYMBOL_CHAR || this == ALTERNATIVE_MINUS_SYMBOL_CHAR
internal fun Char.isDivideSymbol() = this == DIVIDE_SYMBOL_CHAR || this == ALTERNATIVE_DIVIDE_SYMBOL_CHAR
internal fun Char.isMultiplySimbol() = this == MULTIPLY_SYMBOL_CHAR || this == ALTERNATIVE_MULTIPLY_SYMBOL_CHAR