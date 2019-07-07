package com.manueldidonna.architexcalc.main.numkeyboard

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.BindViews
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.arch.view.emitOnClick
import com.manueldidonna.arch.view.emitOnLongClick
import com.manueldidonna.architex.mathevaluator.MathOperator
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseContainerView

class NumKeyboardView(view: View) : BaseContainerView(view),
    ArxInteractiveView<NumKeyboardEvent> by ArxSubjectView() {

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
            emitOnClick(button, NumKeyboardEvent.Number(index.toString()))
        }
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