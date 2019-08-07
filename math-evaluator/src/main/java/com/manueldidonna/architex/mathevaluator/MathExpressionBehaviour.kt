package com.manueldidonna.architex.mathevaluator

import java.lang.StringBuilder

interface MathExpressionBehaviour {
    fun saniteExpression(stringBuilder: StringBuilder): StringBuilder
    fun onDeleteElementCallback(deletedElement: MathElement)
}