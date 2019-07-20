package com.manueldidonna.architexcalc.history.ui.clearhistory

import android.os.Bundle
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.checkbox.isCheckPromptChecked
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.arch.controller.setTarget
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.history.R

class ClearHistoryDialog : BaseDialogController() {

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog {
        title(R.string.dialog_clear_history_title)
        checkBoxPrompt(R.string.dialog_clear_history_prompt) {}
        negativeButton(R.string.dialog_clear_history_action_negative)
        positiveButton(R.string.dialog_clear_history_action_positive) { materialDialog ->
            castTargetController<Listener>().apply {
                onClearHistoryCallBack()
                onAskAgainToClearHistoryCallBack(!materialDialog.isCheckPromptChecked())
            }
        }
    }

    interface Listener {
        fun onClearHistoryCallBack()
        fun onAskAgainToClearHistoryCallBack(askAgain: Boolean)
    }

    companion object {
        fun <T> show(router: Router, target: T) where T : Controller, T : Listener {
            return ClearHistoryDialog()
                .setTarget(target)
                .showDialog(router)
        }
    }
}