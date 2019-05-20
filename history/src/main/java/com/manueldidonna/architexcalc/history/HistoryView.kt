package com.manueldidonna.architexcalc.history

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindString
import butterknife.BindView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.manueldidonna.architexcalc.core.base.BaseContainerView
import com.manueldidonna.architexcalc.core.extensions.setRecyclerView
import com.manueldidonna.architexcalc.core.ui.FixedDividerItemDecoration
import com.manueldidonna.architexcalc.history.data.epoxy.HistoryEpoxyController
import timber.log.Timber


class HistoryView(view: View) : BaseContainerView<HistoryState, HistoryEvent>(view) {
    @BindString(R2.string.history_toolbar_title)
    lateinit var historyTitleValue: String
    @BindView(R2.id.toolbar_navigation_icon)
    lateinit var navigationIcon: AppCompatImageView
    @BindView(R2.id.toolbar_title)
    lateinit var toolbarTitle: AppCompatTextView
    @BindView(R2.id.history_recycler)
    lateinit var recycler: EpoxyRecyclerView
    @BindView(R2.id.clear_history_extended_fab)
    lateinit var clearHistoryFab: ExtendedFloatingActionButton
    @BindView(R2.id.empty_history_view)
    lateinit var emptyHistoryView: LinearLayout

    private val epoxyController = HistoryEpoxyController { id, isSelected ->
        Timber.d("Callback invoked")
        emit(HistoryEvent.SelectItem(itemId = id, isSelected = isSelected))
    }

    init {
        navigationIcon.emitOnClick(HistoryEvent.GoBack)
        clearHistoryFab.setRecyclerView(recycler)
        recycler.apply {
            addItemDecoration(FixedDividerItemDecoration(recycler.context, LinearLayout.VERTICAL))
            setController(epoxyController)
        }

        bind(HistoryState::data) { data ->
            emptyHistoryView.visibility = if (data.isNotEmpty()) View.GONE else View.VISIBLE
            clearHistoryFab.visibility = if (data.isEmpty()) View.GONE else View.VISIBLE
        }

        bind({ Pair(it.data, it.selectedItems) }) { (data, selectedItems) ->
            epoxyController.setData(data, selectedItems > 0)
        }

        bind(HistoryState::selectedItems) { selectedItems ->
            if (selectedItems > 0) {
                toolbarTitle.text = selectedItems.toString()
                clearHistoryFab.apply{
                    text = view.context.getString(R.string.history_remove_items_button_label)
                    emitOnClick(HistoryEvent.ClearSelectedItems)
                }
            } else {
                toolbarTitle.text = historyTitleValue
                clearHistoryFab.apply{
                    text = view.context.getString(R.string.history_clear_button_label)
                    emitOnClick(HistoryEvent.ClearHistory)
                }
            }
        }
    }

}