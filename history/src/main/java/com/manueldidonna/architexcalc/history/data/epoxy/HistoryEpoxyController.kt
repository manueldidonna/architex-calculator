package com.manueldidonna.architexcalc.history.data.epoxy

import android.os.Handler
import com.airbnb.epoxy.EpoxyController
import com.manueldidonna.architexcalc.core.getAsyncHandler
import com.manueldidonna.architexcalc.history.data.HistoryItem

private val asyncEpoxyHandler: Handler = getAsyncHandler("epoxy")

class HistoryEpoxyController(
    private val callback: (Long, Boolean) -> Unit
) : EpoxyController(asyncEpoxyHandler, asyncEpoxyHandler) {

    private var data: List<HistoryItem> = listOf()
    private var isActive: Boolean = false

    init {
        isDebugLoggingEnabled = true
    }

    fun setData(data: List<HistoryItem>, isActive: Boolean) {
        this.data = data
        this.isActive = isActive
        requestModelBuild()
    }

    // fun setDataWithDelay(delay: Int, data: List<HistoryItem>, isActive: Boolean) {
    //     this.data = data
    //     this.isActive = isActive
    //     requestDelayedModelBuild(delay)
    // }

    override fun buildModels() {
        data.forEach { item ->
            epoxyHistoryItem {
                id("item.${item.id}")
                expression(item.expression)
                result(item.result)
                showCheckBox(isActive)
                selected(item.isSelected)
                listener { isSelected: Boolean ->
                    callback(item.id, isSelected)
                }
            }
        }
    }
}