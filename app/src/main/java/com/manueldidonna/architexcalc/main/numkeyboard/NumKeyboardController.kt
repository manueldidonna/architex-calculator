package com.manueldidonna.architexcalc.main.numkeyboard

import android.view.View
import com.manueldidonna.arch.viewmodel.getArxViewModel
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.main.MainViewModel
import com.manueldidonna.architexcalc.core.base.BaseController


class NumKeyboardController : BaseController<NumKeyboardView>() {

    // TODO: il layout va assolutamente ottimizzato
    override val layoutRes: Int = R.layout.controller_num_keyboard

    override fun onCreateControllerView(layoutView: View): NumKeyboardView {
        return NumKeyboardView(layoutView)
    }

    override fun onControllerViewCreated(layoutView: View, controllerView: NumKeyboardView) {
        val parentViewModel = getArxViewModel<MainViewModel>(requireParentViewModelOwner())
        controllerView.subscribeUserInteractionEvents { event: NumKeyboardEvent ->
            parentViewModel.dispatch(event)
        }
    }

}