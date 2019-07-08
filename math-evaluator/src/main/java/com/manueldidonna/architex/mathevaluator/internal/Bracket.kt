package com.manueldidonna.architex.mathevaluator.internal

interface HasOpenBracket

internal interface Bracket : MathElement

internal class OpenBracket : Bracket, HasOpenBracket {
    override val value = "("
}

internal class CloseBracket : Bracket {
    override val value = ")"
}