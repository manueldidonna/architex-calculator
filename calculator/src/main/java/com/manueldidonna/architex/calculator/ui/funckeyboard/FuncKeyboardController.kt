package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.view.View
import com.manueldidonna.arch.controller.castParentController
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.calculator.ui.CalculatorController
import com.manueldidonna.architex.calculator.ui.CalculatorState
import com.manueldidonna.architex.calculator.ui.CalculatorStateManager
import com.manueldidonna.architex.mathevaluator.Exp4JEvaluator
import com.manueldidonna.architex.mathevaluator.MathOperator
import com.manueldidonna.architexcalc.core.base.BaseStatefulController

class FuncKeyboardController : BaseStatefulController<CalculatorStateManager, CalculatorState>() {

    override val saveStateOnProcessDeath = false

    override val stateManager: CalculatorStateManager by lazy {
        castParentController<CalculatorController>().stateManager
    }

    override val layoutRes: Int = R.layout.controller_func_keyboard

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        addView(FuncKeyboardView(view, functions, constants))
            .onEvents { ev ->
                when (ev) {
                    is FuncKeyboardEvent.Function -> stateManager.appendFunction(ev.func)
                    is FuncKeyboardEvent.Bracket -> stateManager.appendBracket(ev.bracket)
                    is FuncKeyboardEvent.Constant -> stateManager.appendConstant(ev.constant)
                    FuncKeyboardEvent.ExpOperator -> stateManager.appendOperator(MathOperator.EXP)
                    FuncKeyboardEvent.ScientificNotation -> stateManager.appendScientificNotation()
                }
            }
    }

    companion object {
        private val functions = listOf(
            Exp4JEvaluator.COS_FUNC,
            Exp4JEvaluator.SIN_FUNC,
            Exp4JEvaluator.TAN_FUNC,
            Exp4JEvaluator.LN_FUNC,
            Exp4JEvaluator.LOG10_FUNC,
            Exp4JEvaluator.SQRT_FUNC,
            Exp4JEvaluator.EULER_EXP_FUNC
        )
        private val constants = listOf(Exp4JEvaluator.PI_CONST, Exp4JEvaluator.EULER_CONST)
    }
}