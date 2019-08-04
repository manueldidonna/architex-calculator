package com.manueldidonna.architexcalc.history.ui

import com.manueldidonna.architexcalc.core.base.BaseStateManager
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.core.prefs.CorePreferences
import com.manueldidonna.architexcalc.core.prefs.Preferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.history.domain.DeleteAllHistoryItemsInteractor
import com.manueldidonna.architexcalc.history.domain.DeleteHistoryItemsInteractor
import com.manueldidonna.architexcalc.history.domain.GetHistoryItemsInteractor

class HistoryStateManager(
    initialState: HistoryState,
    getHistoryItems: GetHistoryItemsInteractor,
    private val deleteAllHistoryItems: DeleteAllHistoryItemsInteractor,
    private val deleteHistoryItems: DeleteHistoryItemsInteractor,
    private val prefsManager: PreferencesManager
) : BaseStateManager<HistoryState>(initialState) {

    init {
        prefsManager.observe(DESCENDANT_HISTORY_ORDER)
            .distinctUntilChanged()
            .autoSubscribe { newValue ->
                setState { copy(data = data.reversed(), isDescendantHistoryOrder = newValue) }
            }
        prefsManager.observe(CorePreferences.ASK_TO_CLEAR_HISTORY).autoSubscribe { newValue ->
            setState { copy(askToClearHistory = newValue) }
        }
        getHistoryItems().observe().autoSubscribe { items ->
            setState { copy(data = if (isDescendantHistoryOrder) items.reversed() else items) }
        }
    }

    fun updateHistoryOrder(isDescendantOrder: Boolean) {
        prefsManager.set(DESCENDANT_HISTORY_ORDER, isDescendantOrder)
    }

    fun setAskConfirmation(value: Boolean) {
        prefsManager.set(CorePreferences.ASK_TO_CLEAR_HISTORY, value)
    }

    fun deleteHistoryItems() {
        stateManagerScope.launchInteractor(deleteAllHistoryItems)
    }

    fun deleteSelectedHistoryItems() = setState {
        if (selectedItemIds.isNotEmpty())
            stateManagerScope.launchInteractor(deleteHistoryItems, selectedItemIds.toList())
        return@setState copy(selectedItemIds = hashSetOf())
    }

    fun selectItemById(id: Long, isSelected: Boolean) = setState {
        val newSet = HashSet(selectedItemIds)
        if (isSelected) newSet.add(id) else newSet.remove(id)
        return@setState copy(selectedItemIds = newSet)
    }

    fun deselectAllItems() = setState { copy(selectedItemIds = hashSetOf()) }

    companion object {
        private val DESCENDANT_HISTORY_ORDER = Preferences.BooleanPreference("desc_history_order")
    }
}