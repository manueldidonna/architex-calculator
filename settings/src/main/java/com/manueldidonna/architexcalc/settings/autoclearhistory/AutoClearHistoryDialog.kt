package com.manueldidonna.architexcalc.settings.autoclearhistory

import android.os.Bundle
import androidx.core.os.bundleOf
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.settings.R
import com.manueldidonna.architexcalc.settings.SettingsState

internal class AutoClearHistoryDialog : BaseDialogController {

    private var initialIndex: Int = 0

    constructor(args: Bundle) : super(args) {
        initialIndex = getArgs().getInt(INITIAL_INDEX_KEY, 0)
    }

    private constructor(
        target: Controller,
        initialIndex: Int
    ) : this(bundleOf(INITIAL_INDEX_KEY to initialIndex)) {
        targetController = target
    }

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog {
        listItemsSingleChoice(
            res = R.array.settings_auto_clear_history_values,
            initialSelection = initialIndex,
            waitForPositiveButton = false
        ) { dialog: MaterialDialog, index: Int, _ ->
            castTargetController<Listener>()
                .onAutoClearHistoryCallback(SettingsState.getDaysFromIndex(index))
            dialog.dismiss()
        }
    }

    interface Listener {
        fun onAutoClearHistoryCallback(days: Int)
    }

    companion object {
        private const val INITIAL_INDEX_KEY = "auto_clear_dialog:initial_index"

        fun <T> show(router: Router, target: T, initialDaysIndex: Int) where T : Controller, T : Listener {
            AutoClearHistoryDialog(target, initialDaysIndex).showDialog(router)
        }
    }
}