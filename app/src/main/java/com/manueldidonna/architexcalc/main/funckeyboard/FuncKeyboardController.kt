package com.manueldidonna.architexcalc.main.funckeyboard

import android.view.View
import com.manueldidonna.arch.viewmodel.getArxViewModel
import com.manueldidonna.architexcalc.main.MainViewModel
import com.manueldidonna.architexcalc.core.base.BaseController

class FuncKeyboardController : BaseController<FuncKeyboardView>() {

    override val layoutRes: Int = TODO("Create R.layout.controller_func_keyboard")

    override fun onCreateControllerView(layoutView: View) = FuncKeyboardView(layoutView)

    override fun onControllerViewCreated(layoutView: View, controllerView: FuncKeyboardView) {
        val parentViewModel = getArxViewModel<MainViewModel>(requireParentViewModelOwner())
        // TODO: dispatch view events to parentViewModel
    }

}