package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.view.View
import com.manueldidonna.arch.conductorLazy
import com.manueldidonna.arch.controller.castParentController
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architex.calculator.ui.CalculatorController
import com.manueldidonna.architex.calculator.ui.createconstant.CreateConstantDialog
import com.manueldidonna.architex.mathevaluator.evaluator.Exp4JEvaluator
import com.manueldidonna.architexcalc.core.base.BaseStatefulController

internal class FuncKeyboardController : BaseStatefulController<FuncKeyboardStateManager, FuncKeyboardState>() {

    override val saveStateOnProcessDeath = false

    override val stateManager by conductorLazy { controllerScope.get<FuncKeyboardStateManager>() }

    override val layoutRes: Int = R.layout.controller_func_keyboard

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        val calcStateManager = castParentController<CalculatorController>().stateManager
        addView(FuncKeyboardView(view, calcStateManager, stateManager, functions))
            .subscribeToStateChanges()
            .onEvents { CreateConstantDialog.show(router) }
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
    }
}