package com.manueldidonna.architexcalc.main.funckeyboard

import android.view.View
import com.manueldidonna.arch.viewmodel.getArxViewModel
import com.manueldidonna.architex.mathevaluator.Exp4JEvaluator
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseController
import com.manueldidonna.architexcalc.main.MainViewModel

class FuncKeyboardController : BaseController<FuncKeyboardView>() {

    override val layoutRes: Int = R.layout.controller_func_keyboard

    override fun onCreateControllerView(layoutView: View) = FuncKeyboardView(layoutView, functions, constants)

    override fun onControllerViewCreated(layoutView: View, controllerView: FuncKeyboardView) {
        val parentViewModel = getArxViewModel<MainViewModel>(requireParentViewModelOwner())
        controllerView.subscribeUserInteractionEvents { event: FuncKeyboardEvent ->
            parentViewModel.dispatch(event)
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