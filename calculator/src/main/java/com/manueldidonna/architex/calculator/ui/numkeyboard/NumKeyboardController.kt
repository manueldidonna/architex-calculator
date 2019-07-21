package com.manueldidonna.architex.calculator.ui.numkeyboard

import android.view.View
import com.manueldidonna.arch.controller.castParentController
import com.manueldidonna.arch.viewmodel.getArxViewModel
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.calculator.ui.CalculatorController
import com.manueldidonna.architex.calculator.ui.CalculatorState
import com.manueldidonna.architex.calculator.ui.CalculatorStateManager
import com.manueldidonna.architexcalc.core.base.BaseStatefulController


class NumKeyboardController : BaseStatefulController<CalculatorStateManager, CalculatorState>() {

    override val saveStateOnProcessDeath = false

    override val stateManager: CalculatorStateManager by lazy {
        castParentController<CalculatorController>().stateManager
    }

    // TODO: il layout va assolutamente ottimizzato
    override val layoutRes: Int = R.layout.controller_num_keyboard

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        addView(NumKeyboardView(view)).onEvents { event ->
            when (event) {
                is NumKeyboardEvent.Number -> stateManager.appendNumber(event.value)
                NumKeyboardEvent.Cancel -> stateManager.deleteLastElement()
                NumKeyboardEvent.Clear -> stateManager.clearExpression()
                NumKeyboardEvent.Equal -> stateManager.clearResultAndSaveToHistory()
                NumKeyboardEvent.DecimalSeparator -> stateManager.appendDecimalSeparator()
                is NumKeyboardEvent.Operator -> stateManager.appendOperator(event.value)
            }
        }
    }
}