package com.manueldidonna.architexcalc.history.ui.historyorder

import android.os.Bundle
import androidx.core.os.bundleOf
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.manueldidonna.arch.controller.castTargetController
import com.manueldidonna.arch.controller.setTarget
import com.manueldidonna.architexcalc.core.base.BaseDialogController
import com.manueldidonna.architexcalc.history.R

internal class UpdateHistoryOrderDialog(args: Bundle) : BaseDialogController(args) {

    private var initialIndex: Int = getArgs().getInt(INITIAL_INDEX_KEY, 0)

    override fun onCreateDialog(savedViewState: Bundle?) = materialDialog {
        listItemsSingleChoice(
            res = R.array.dialog_update_history_order_values,
            initialSelection = initialIndex
        ) { _, index: Int, _ ->
            castTargetController<Listener>().onHistoryOrderUpdatedCallback(index != 0)
        }
    }

    interface Listener {
        fun onHistoryOrderUpdatedCallback(isDescendantOrder: Boolean)
    }

    companion object {
        private const val INITIAL_INDEX_KEY = "update_history_order_dialog:initial_index"

        fun <T> show(router: Router, target: T, isDescendantOrder: Boolean) where T : Controller, T : Listener {
            UpdateHistoryOrderDialog(bundleOf(INITIAL_INDEX_KEY to if (isDescendantOrder) 1 else 0))
                .setTarget(target)
                .showDialog(router)
        }
    }
}