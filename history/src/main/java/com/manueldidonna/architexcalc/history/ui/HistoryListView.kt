package com.manueldidonna.architexcalc.history.ui

import android.view.View
import android.widget.LinearLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.manueldidonna.arch.view.ArxBinderView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxStateBinderView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.architexcalc.core.extensions.find
import com.manueldidonna.architexcalc.core.extensions.setRecyclerView
import com.manueldidonna.architexcalc.core.ui.FixedDividerItemDecoration
import com.manueldidonna.architexcalc.history.R

internal sealed class HistoryListEvent {
    object ClearHistory : HistoryListEvent()
    object ClearSelectedItems : HistoryListEvent()
    data class ChangeItemSelection(val itemId: Long, val isSelected: Boolean) : HistoryListEvent()
}

internal class HistoryListView(view: View) : ArxBinderView<HistoryState> by ArxStateBinderView(),
    ArxInteractiveView<HistoryListEvent> by ArxSubjectView() {
    private val recyclerView = view.find<EpoxyRecyclerView>(R.id.history_recycler)
    private val clearHistoryFab = view.find<ExtendedFloatingActionButton>(R.id.clear_history_extended_fab)
    private val emptyHistoryView = view.find<LinearLayout>(R.id.empty_history_view)

    private val epoxyController = HistoryEpoxyController { id, isSelected ->
        emitEvent(HistoryListEvent.ChangeItemSelection(itemId = id, isSelected = isSelected))
    }

    private var isSelectionModeActive = false

    init {
        clearHistoryFab.setRecyclerView(recyclerView)
        clearHistoryFab.setOnClickListener {
            emitEvent(if (isSelectionModeActive) HistoryListEvent.ClearSelectedItems else HistoryListEvent.ClearHistory)
        }
        recyclerView.apply {
            setController(epoxyController)
            addItemDecoration(FixedDividerItemDecoration(recyclerView.context, LinearLayout.VERTICAL))
        }
        bind({ Pair(it.data, it.selectedItemIds) }) { (data, selectedItemIds) ->
            epoxyController.setData(data, selectedItemIds)
        }
        bind(HistoryState::data) { data ->
            emptyHistoryView.visibility = if (data.isNotEmpty()) View.GONE else View.VISIBLE
            clearHistoryFab.visibility = if (data.isEmpty()) View.GONE else View.VISIBLE
        }
        bind(HistoryState::selectedItemIds) { selectedItemIds ->
            isSelectionModeActive = selectedItemIds.isNotEmpty()
            if (isSelectionModeActive)
                clearHistoryFab.text = view.resources.getString(R.string.history_remove_items_button_label)
            else
                clearHistoryFab.text = view.resources.getString(R.string.history_clear_button_label)
        }
    }
}
