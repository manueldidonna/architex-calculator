package com.manueldidonna.architexcalc.history

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindString
import butterknife.BindView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseContainerView
import com.manueldidonna.architexcalc.core.extensions.iterateAndBuildModels
import com.manueldidonna.architexcalc.core.extensions.setRecyclerView
import com.manueldidonna.architexcalc.core.ui.FixedDividerItemDecoration

class HistoryView(view: View) : BaseContainerView<HistoryState, HistoryEvent>(view) {

    @BindString(R.string.calculator_history)
    lateinit var historyLabel: String
    @BindView(R.id.toolbar_navigation_icon)
    lateinit var navigationIcon: AppCompatImageView
    @BindView(R.id.toolbar_title)
    lateinit var toolbarTitle: AppCompatTextView
    @BindView(R.id.history_recycler)
    lateinit var recycler: EpoxyRecyclerView
    @BindView(R.id.delete_history_extended_fab)
    lateinit var deleteHistoryFab: ExtendedFloatingActionButton
    @BindView(R.id.empty_history_view)
    lateinit var emptyHistoryView: LinearLayout

    init {
        // View initialization
        toolbarTitle.text = historyLabel
        navigationIcon.emitOnClick(HistoryEvent.GoBack)
        recycler.addItemDecoration(FixedDividerItemDecoration(recycler.context, LinearLayout.VERTICAL))
        deleteHistoryFab.apply {
            setRecyclerView(recycler)
            emitOnClick(HistoryEvent.DeleteHistory)
        }
        // View binding
        bind(HistoryState::data) { data ->
            emptyHistoryView.visibility = if (data.isNotEmpty()) View.GONE else View.VISIBLE
            deleteHistoryFab.visibility = if (data.isEmpty()) View.GONE else View.VISIBLE
            recycler.iterateAndBuildModels(data) { _, item ->
                epoxyHistoryItem {
                    id("item.${item.id}")
                    expression(item.expression)
                    result(item.result)
                }
            }
        }
    }
}