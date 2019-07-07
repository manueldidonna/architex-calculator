package com.manueldidonna.architexcalc.history

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindString
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.manueldidonna.arch.view.*
import com.manueldidonna.architexcalc.core.base.BaseContainerView
import com.manueldidonna.architexcalc.core.extensions.setRecyclerView
import com.manueldidonna.architexcalc.core.ui.FixedDividerItemDecoration
import com.manueldidonna.architexcalc.history.data.epoxy.HistoryEpoxyController


class HistoryView(view: View) : BaseContainerView(view),
    ArxBinderView<HistoryState> by ArxStateBinderView(),
    ArxInteractiveView<HistoryEvent> by ArxSubjectView() {

    @BindString(R2.string.history_toolbar_title)
    lateinit var historyTitleValue: String
    private val navigationIcon by findView<AppCompatImageView>(R.id.toolbar_navigation_icon)
    private val toolbarTitle by findView<AppCompatTextView>(R.id.toolbar_title)
    private val recycler by findView<EpoxyRecyclerView>(R.id.history_recycler)
    private val clearHistoryFab by findView<ExtendedFloatingActionButton>(R.id.clear_history_extended_fab)
    private val emptyHistoryView by findView<LinearLayout>(R.id.empty_history_view)

    private val epoxyController = HistoryEpoxyController { id, isSelected ->
        emitEvent(HistoryEvent.SelectItem(itemId = id, isSelected = isSelected))
    }

    private var isSelectionActive = false

    init {
        emitOnClick(navigationIcon, HistoryEvent.GoBack)
        clearHistoryFab.setRecyclerView(recycler)
        recycler.apply {
            setController(epoxyController)
            addItemDecoration(FixedDividerItemDecoration(recycler.context, LinearLayout.VERTICAL))
        }

        bind({ Pair(it.data, it.selectedItems) }) { (data, selectedItems) ->
            epoxyController.setData(data, selectedItems > 0)
        }

        bind(HistoryState::data) { data ->
            emptyHistoryView.visibility = if (data.isNotEmpty()) View.GONE else View.VISIBLE
            clearHistoryFab.visibility = if (data.isEmpty()) View.GONE else View.VISIBLE
        }

        bind(HistoryState::selectedItems) { selectedItems ->
            if (selectedItems > 0) {
                toolbarTitle.text = selectedItems.toString()
                clearHistoryFab.text = view.context.getString(R.string.history_remove_items_button_label)
                if (!isSelectionActive)
                    emitOnClick(clearHistoryFab, HistoryEvent.ClearSelectedItems)
                isSelectionActive = true
            } else {
                toolbarTitle.text = historyTitleValue
                clearHistoryFab.text = view.context.getString(R.string.history_clear_button_label)
                emitOnClick(clearHistoryFab, HistoryEvent.ClearHistory)
                isSelectionActive = false
            }
        }
    }
}
