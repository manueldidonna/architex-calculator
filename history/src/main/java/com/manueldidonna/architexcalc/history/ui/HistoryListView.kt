package com.manueldidonna.architexcalc.history.ui

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
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
    private val clearHistoryFab = view.find<ExtendedFloatingActionButton>(R.id.clear_history_extended_fab)
    private val emptyHistoryView = view.find<LinearLayout>(R.id.empty_history_view)
    private val recyclerView = view.find<EpoxyRecyclerView>(R.id.history_recycler) {
        addItemDecoration(FixedDividerItemDecoration(context, LinearLayout.VERTICAL))
    }

    private val epoxyController = HistoryEpoxyController { id, isSelected ->
        emitEvent(HistoryListEvent.ChangeItemSelection(itemId = id, isSelected = isSelected))
    }

    private var isSelectionModeActive = false
    private var dataSize = 0

    init {
        recyclerView.scrollToTopOnItemRangeMoved()
        recyclerView.setController(epoxyController)
        clearHistoryFab.setRecyclerView(recyclerView)
        clearHistoryFab.setOnClickListener {
            if (isSelectionModeActive)
                emitEvent(HistoryListEvent.ClearSelectedItems)
            else
                emitEvent(HistoryListEvent.ClearHistory)
        }

        bind({ Pair(it.data, it.selectedItemIds) }) { (data, selectedItemIds) ->
            recyclerView.updateItemAnimator(data.size)
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

    private fun RecyclerView.updateItemAnimator(newDataSize: Int) {
        if (dataSize != newDataSize && recyclerView.itemAnimator == null)
            itemAnimator = defaultItemAnimator
        else if (dataSize == newDataSize)
            itemAnimator = null
        dataSize = newDataSize
    }

    private fun RecyclerView.scrollToTopOnItemRangeMoved() {
        epoxyController.adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                scrollToPosition(0)
            }
        })
    }

    companion object {
        private val defaultItemAnimator = DefaultItemAnimator()
    }
}
