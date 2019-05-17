package com.manueldidonna.architexcalc.history.clearhistory

import android.os.Bundle
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.checkbox.isCheckPromptChecked
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.history.R

class ClearHistoryDialog : BaseDialogController {

    constructor() : super()
    private constructor(target: Controller) : this() {
        targetController = target
    }

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog {
        title(R.string.dialog_clear_history_title)
        checkBoxPrompt(R.string.dialog_clear_history_prompt) {}
        negativeButton(R.string.dialog_clear_history_action_negative)
        positiveButton(R.string.dialog_clear_history_action_positive) { materialDialog ->
            castTargetController<Listener>().apply {
                onClearHistoryCallBack()
                onAskAgainCallBack(!materialDialog.isCheckPromptChecked())
            }
        }
    }

    interface Listener {
        fun onClearHistoryCallBack()
        fun onAskAgainCallBack(askAgain: Boolean)
    }

    companion object {
        fun <T> show(router: Router, target: T) where T : Controller, T : Listener {
            return ClearHistoryDialog(target).showDialog(router)
        }
    }
}