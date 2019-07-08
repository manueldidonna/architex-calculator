package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.MathElement

/**
 * [name] is the name of the constant. Es: 'pi', 'e', ...
 * [value] is the value of che constant
 */
data class MathConstant(val name: String, override val value: String) : MathElement