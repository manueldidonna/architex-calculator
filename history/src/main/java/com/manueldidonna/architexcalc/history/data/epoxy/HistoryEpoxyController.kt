package com.manueldidonna.architexcalc.history.data.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import com.manueldidonna.architexcalc.history.data.HistoryItem

class HistoryEpoxyController(
    private val callback: (Long, Boolean) -> Unit
) : Typed2EpoxyController<List<HistoryItem>, Boolean>() {
    override fun buildModels(data: List<HistoryItem>, isActive: Boolean) {
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