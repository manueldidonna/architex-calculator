package com.manueldidonna.architexcalc.history.ui

import android.view.View
import com.manueldidonna.arch.conductorLazy
import com.manueldidonna.arch.statemanager.withState
import com.manueldidonna.architexcalc.core.base.BaseStatefulController
import com.manueldidonna.architexcalc.history.R
import com.manueldidonna.architexcalc.history.ui.clearhistory.ClearHistoryDialog

class HistoryController : BaseStatefulController<HistoryStateManager, HistoryState>(), ClearHistoryDialog.Listener {

    override val saveStateOnProcessDeath = false

    override val stateManager by conductorLazy { controllerScope.get<HistoryStateManager>() }

    override val layoutRes: Int = R.layout.controller_history

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        addView(HistoryToolbarView(view))
            .subscribeToStateChanges(HistoryState::selectedItemIds) { it.size }
            .onEvents { router.popController(this) }
        addView(HistoryListView(view))
            .subscribeToStateChanges()
            .onEvents { ev ->
                when (ev) {
                    is HistoryListEvent.ClearHistory -> clearHistory()
                    is HistoryListEvent.ClearSelectedItems -> stateManager.deleteSelectedHistoryItems()
                    is HistoryListEvent.ChangeItemSelection -> stateManager.selectItemById(ev.itemId, ev.isSelected)
                }
            }
    }

    private fun clearHistory() = withState { state ->
        if (!state.askToClearHistory)
            stateManager.deleteHistoryItems()
        else ClearHistoryDialog.show(router, this)
    }

    override fun onAskAgainToClearHistoryCallBack(askAgain: Boolean) {
        stateManager.setAskConfirmation(askAgain)
    }

    override fun onClearHistoryCallBack() {
        stateManager.deleteHistoryItems()
    }

    override fun handleBack(): Boolean = withState { state ->
        if (state.selectedItemIds.isNotEmpty())
            stateManager.deselectAllItems()
        return@withState state.selectedItemIds.isNotEmpty()
    }
}