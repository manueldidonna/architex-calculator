package com.manueldidonna.architexcalc.settings.ui.shared

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxSubjectView
import com.manueldidonna.arch.view.emitOnClick
import com.manueldidonna.architexcalc.common.ui.extensions.find
import com.manueldidonna.architexcalc.settings.R

internal object ToolbarGoBackEvent

internal class ToolbarView(view: View, title: String) : ArxInteractiveView<ToolbarGoBackEvent> by ArxSubjectView() {
    private val navigationIconView = view.find<AppCompatImageView>(R.id.toolbar_navigation_icon)
    init {
        view.find<AppCompatTextView>(R.id.toolbar_title).text = title
        emitOnClick(navigationIconView, ToolbarGoBackEvent)
    }
}