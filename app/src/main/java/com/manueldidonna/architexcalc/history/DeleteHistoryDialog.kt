package com.manueldidonna.architexcalc.history

import android.app.Dialog
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.checkbox.isCheckPromptChecked
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.ArxDialogController
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.architexcalc.R

class DeleteHistoryDialog : ArxDialogController {

    constructor() : super()
    private constructor(target: Controller) : this() {
        targetController = target
    }

    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        return MaterialDialog(requireActivity())
            .title(R.string.dialog_delete_history_title)
            .checkBoxPrompt(R.string.dialog_delete_history_prompt) {}
            .negativeButton(R.string.dialog_delete_history_action_negative)
            .positiveButton(R.string.dialog_delete_history_action_positive) { materialDialog ->
                castTargetController<Listener>().apply {
                    onDeleteHistoryCallBack()
                    onAskAgainCallBack(!materialDialog.isCheckPromptChecked())
                }
            }
    }

    interface Listener {
        fun onDeleteHistoryCallBack()
        fun onAskAgainCallBack(askAgain: Boolean)
    }

    companion object {
        fun <T> show(router: Router, target: T) where T : Controller, T : Listener {
            return DeleteHistoryDialog(target).showDialog(router)
        }
    }
}