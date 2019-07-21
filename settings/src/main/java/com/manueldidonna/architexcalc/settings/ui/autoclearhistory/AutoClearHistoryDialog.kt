package com.manueldidonna.architexcalc.settings.ui.autoclearhistory

import android.os.Bundle
import androidx.core.os.bundleOf
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.arch.controller.setTarget
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.settings.R
import com.manueldidonna.architexcalc.settings.ui.SettingsState

internal class AutoClearHistoryDialog(args: Bundle) : BaseDialogController(args) {

    private var initialIndex: Int = getArgs().getInt(INITIAL_INDEX_KEY, 0)

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog {
        listItemsSingleChoice(
            res = R.array.settings_auto_clear_history_values,
            initialSelection = initialIndex,
            waitForPositiveButton = false
        ) { _, index: Int, _ ->
            castTargetController<Listener>()
                .onAutoClearHistoryCallback(SettingsState.getDaysFromIndex(index))
        }
    }

    interface Listener {
        fun onAutoClearHistoryCallback(days: Int)
    }

    companion object {
        private const val INITIAL_INDEX_KEY = "auto_clear_dialog:initial_index"

        fun <T> show(router: Router, target: T, initialDaysIndex: Int) where T : Controller, T : Listener {
            AutoClearHistoryDialog(bundleOf(INITIAL_INDEX_KEY to initialDaysIndex))
                .setTarget(target)
                .showDialog(router)
        }
    }
}