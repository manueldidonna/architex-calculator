package com.manueldidonna.architexcalc.history.ui

import android.os.Handler
import com.airbnb.epoxy.EpoxyController
import com.manueldidonna.architexcalc.core.BuildConfig
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import com.manueldidonna.architexcalc.core.getAsyncHandler

private val asyncEpoxyHandler: Handler = getAsyncHandler("epoxy")

class HistoryEpoxyController(
    private val callback: (Long, Boolean) -> Unit
) : EpoxyController(asyncEpoxyHandler, asyncEpoxyHandler) {

    private var data: List<HistoryEntity> = listOf()
    private var selectedIds: Set<Long> = setOf()

    init {
        isDebugLoggingEnabled = BuildConfig.DEBUG
    }

    fun setData(data: List<HistoryEntity>, selectedIds: Set<Long>) {
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