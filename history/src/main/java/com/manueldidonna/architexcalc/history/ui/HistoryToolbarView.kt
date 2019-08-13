package com.manueldidonna.architexcalc.history.ui

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxRenderView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.arch.view.emitOnClick
import com.manueldidonna.architexcalc.common.ui.extensions.find
import com.manueldidonna.architexcalc.history.R

internal sealed class HistoryToolbarEvent {
    object GoBack : HistoryToolbarEvent()
    object UpdateHistoryOrder : HistoryToolbarEvent()
}

internal class HistoryToolbarView(view: View) : ArxRenderView<Int>,
    ArxInteractiveView<HistoryToolbarEvent> by ArxSubjectView() {
    private val toolbarTitle = view.resources.getString(R.string.history_toolbar_title)
    private val titleView = view.find<AppCompatTextView>(R.id.toolbar_title)
    private val navigationIconView = view.find<AppCompatImageView>(R.id.toolbar_navigation_icon)
    private val invertHistoryOrderIconView = view.find<View>(R.id.history_order_icon)

    init {
        emitOnClick(navigationIconView, HistoryToolbarEvent.GoBack)
        emitOnClick(invertHistoryOrderIconView, HistoryToolbarEvent.UpdateHistoryOrder)
    }

    override fun render(state: Int) {
        titleView.text = if (state > 0) state.toString() else toolbarTitle
    }
}