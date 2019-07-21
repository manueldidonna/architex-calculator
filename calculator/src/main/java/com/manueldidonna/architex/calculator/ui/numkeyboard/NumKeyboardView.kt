package com.manueldidonna.architex.calculator.ui.numkeyboard

import android.view.View
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.arch.view.emitOnClick
import com.manueldidonna.arch.view.emitOnLongClick
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.mathevaluator.MathOperator
import com.manueldidonna.architexcalc.core.extensions.find

sealed class NumKeyboardEvent {
    object Cancel : NumKeyboardEvent()
    object Clear : NumKeyboardEvent()
    object Equal : NumKeyboardEvent()
    object DecimalSeparator : NumKeyboardEvent()
    data class Number(val value: String) : NumKeyboardEvent()
    data class Operator(val value: MathOperator) : NumKeyboardEvent()
}

class NumKeyboardView(view: View) : ArxInteractiveView<NumKeyboardEvent> by ArxSubjectView() {

    private val zeroButton = view.find<View>(R.id.zero_button)
    private val oneButton = view.find<View>(R.id.one_button)
    private val twoButton = view.find<View>(R.id.two_button)
    private val threeButton = view.find<View>(R.id.three_button)
    private val fourButton = view.find<View>(R.id.four_button)
    private val fiveButton = view.find<View>(R.id.five_button)
    private val sixButton = view.find<View>(R.id.six_button)
    private val sevenButton = view.find<View>(R.id.seven_button)
    private val eightButton = view.find<View>(R.id.eight_button)
    private val nineButton = view.find<View>(R.id.nine_button)
    private val dotButton = view.find<View>(R.id.dot_button)
    private val multiplyButton = view.find<View>(R.id.multiply_button)
    private val divButton = view.find<View>(R.id.div_button)
    private val plusMinusButton = view.find<View>(R.id.plus_minus_button)
    private val equalButton = view.find<View>(R.id.equal_button)
    private val cancelButton = view.find<View>(R.id.cancel_button)

    init {
        // numbers
        emitOnClick(zeroButton, NumKeyboardEvent.Number("0"))
        emitOnClick(oneButton, NumKeyboardEvent.Number("1"))
        emitOnClick(twoButton, NumKeyboardEvent.Number("2"))
        emitOnClick(threeButton, NumKeyboardEvent.Number("3"))
        emitOnClick(fourButton, NumKeyboardEvent.Number("4"))
        emitOnClick(fiveButton, NumKeyboardEvent.Number("5"))
        emitOnClick(sixButton, NumKeyboardEvent.Number("6"))
        emitOnClick(sevenButton, NumKeyboardEvent.Number("7"))
        emitOnClick(eightButton, NumKeyboardEvent.Number("8"))
        emitOnClick(nineButton, NumKeyboardEvent.Number("9"))
        // dot
        emitOnClick(dotButton, NumKeyboardEvent.DecimalSeparator)
        // operators + - / x
        emitOnClick(multiplyButton, NumKeyboardEvent.Operator(MathOperator.MULTIPLY))
        emitOnClick(divButton, NumKeyboardEvent.Operator(MathOperator.DIVIDE))
        emitOnClick(plusMinusButton, NumKeyboardEvent.Operator(MathOperator.PLUS_MINUS))
        // equal
        emitOnClick(equalButton, NumKeyboardEvent.Equal)
        // click -> cancel last char/math func
        emitOnClick(cancelButton, NumKeyboardEvent.Cancel)
        // long click -> clear all
        emitOnLongClick(cancelButton, NumKeyboardEvent.Clear)
    }
}