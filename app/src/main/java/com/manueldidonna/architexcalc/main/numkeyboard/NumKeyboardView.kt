package com.manueldidonna.architexcalc.main.numkeyboard

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.BindViews
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.architex.mathevaluator.DECIMAL_SEPARATOR
import com.manueldidonna.architex.mathevaluator.PLUS_MINUS_SYMBOL
import com.manueldidonna.architexcalc.R

class NumKeyboardView(view: View) : ArxSubjectView<NumKeyboardEvent>(view) {

    @BindViews(
        R.id.zero_button, R.id.one_button, R.id.two_button, R.id.three_button, R.id.four_button,
        R.id.five_button, R.id.six_button, R.id.seven_button, R.id.eight_button, R.id.nine_button
    )
    lateinit var numberButtons: Array<TextView>
    @BindView(R.id.dot_button)
    lateinit var dotButton: TextView
    @BindView(R.id.multiply_button)
    lateinit var multiplyButton: TextView
    @BindView(R.id.div_button)
    lateinit var divButton: TextView
    @BindView(R.id.plus_minus_button)
    lateinit var plusMinusButton: TextView
    @BindView(R.id.equal_button)
    lateinit var equalButton: TextView
    @BindView(R.id.cancel_button)
    lateinit var cancelButton: TextView

    init {
        numberButtons.forEachIndexed { index, button ->
            button.clickInvoke(NumKeyboardEvent.Number(index.toString()))
        }
        // dot
        dotButton.clickInvoke(NumKeyboardEvent.Number(DECIMAL_SEPARATOR.toString()))
        // operators + - / x
        multiplyButton.clickInvoke(NumKeyboardEvent.Operator('×'))
        divButton.clickInvoke(NumKeyboardEvent.Operator('÷'))
        plusMinusButton.clickInvoke(NumKeyboardEvent.Operator(PLUS_MINUS_SYMBOL))
        // equal
        equalButton.clickInvoke(NumKeyboardEvent.Equal)
        // click -> cancel last char/math func
        cancelButton.clickInvoke(NumKeyboardEvent.Cancel)
        // long click -> clear all
        cancelButton.setOnLongClickListener {
            emit(NumKeyboardEvent.Clear)
            return@setOnLongClickListener true
        }
    }

    private fun View.clickInvoke(event: NumKeyboardEvent) {
        setOnClickListener { emit(event) }
    }

}