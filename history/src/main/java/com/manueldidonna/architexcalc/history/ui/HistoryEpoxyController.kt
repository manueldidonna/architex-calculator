package com.manueldidonna.architexcalc.history.ui

import android.os.Handler
import com.airbnb.epoxy.EpoxyController
import com.manueldidonna.architexcalc.core.BuildConfig
import com.manueldidonna.architexcalc.core.getAsyncHandler
import com.manueldidonna.architexcalc.database.History

private val asyncEpoxyHandler: Handler = getAsyncHandler("epoxy")

class HistoryEpoxyController(
    private val callback: (Long, Boolean) -> Unit
) : EpoxyController(asyncEpoxyHandler, asyncEpoxyHandler) {

    private var data: List<History> = listOf()
    private var selectedIds: Set<Long> = setOf()

    init {
        isDebugLoggingEnabled = BuildConfig.DEBUG
    }

    fun setData(data: List<History>, selectedIds: Set<Long>) {
        this.data = data
        this.selectedIds = selectedIds
        requestModelBuild()
    }

    override fun buildModels() {
        val isSelectionModeActive = selectedIds.isNotEmpty()
        data.forEach { item ->
            historyEpoxyView {
                id("item.${item.id}")
                expression(item.expression)
                result(item.result)
                showCheckBox(isSelectionModeActive)
                selected(selectedIds.contains(item.id))
                listener { isSelected: Boolean ->
                    callback(item.id, isSelected)
                }
            }
        }
    }
}