package com.manueldidonna.architexcalc.main.funckeyboard

import android.view.View
import com.manueldidonna.arch.viewmodel.getArxViewModel
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseController
import com.manueldidonna.architexcalc.main.MainViewModel

class FuncKeyboardController : BaseController<FuncKeyboardView>() {

    override val layoutRes: Int = R.layout.controller_func_keyboard

    override fun onCreateControllerView(layoutView: View) = FuncKeyboardView(layoutView, funcs, constants)

    override fun onControllerViewCreated(layoutView: View, controllerView: FuncKeyboardView) {
        val parentViewModel = getArxViewModel<MainViewModel>(requireParentViewModelOwner())
        // TODO: dispatch view events to parentViewModel
    }

    companion object {
        private val funcs = listOf("sin", "cos", "tan", "ln", "log", "\u221a", "(", ")", "%", "^", "!")
        private val constants = listOf("\u03C0", "e")
    }
}