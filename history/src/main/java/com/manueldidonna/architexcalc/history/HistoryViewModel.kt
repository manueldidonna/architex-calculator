package com.manueldidonna.architexcalc.history

import com.manueldidonna.architexcalc.core.base.BaseViewModel
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.core.prefs.CorePreferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.history.data.HistoryItem
import com.manueldidonna.architexcalc.history.data.interactors.DeleteHistoryInteractor
import com.manueldidonna.architexcalc.history.data.interactors.GetHistoryInteractor

class HistoryViewModel(
    initialState: HistoryState,
    private val getHistory: GetHistoryInteractor,
    private val deleteHistory: DeleteHistoryInteractor,
    private val prefsManager: PreferencesManager
) : BaseViewModel<HistoryState>(initialState) {

    init {
        prefsManager.observe(CorePreferences.ASK_CONFIRM).autoSubscribe { newValue ->
            setState { copy(askConfirmation = newValue) }
        }
        getHistory.observe().autoSubscribe { items ->
            setState { copy(data = items) }
        }
        coroutineScope.launchInteractor(getHistory)
    }

    fun setAskConfirmation(value: Boolean) {
        prefsManager.set(CorePreferences.ASK_CONFIRM, value)
    }

    fun deleteHistory() = withState {
        if (it.data.isNotEmpty()) {
            coroutineScope.launchInteractor(
                deleteHistory, DeleteHistoryInteractor.Params(deleteAll = true)
            )
        }
    }

    fun deleteSelectedItems() = setState {
        if (data.isNotEmpty()) {
            val idsToDelete = data.asSequence()
                .filter { it.isSelected }
                .map(HistoryItem::id)
                .toList()
            coroutineScope.launchInteractor(
                deleteHistory, DeleteHistoryInteractor.Params(deleteAll = false, idsToDelete = idsToDelete)
            )
        }
        return@setState copy(selectedItems = 0)
    }

    fun selectItemById(id: Long, isSelected: Boolean) = setState {
        val newData = data.map { item ->
            return@map if (item.id != id) item else {
                HistoryItem(
                    id = item.id,
                    expression = item.expression,
                    result = item.result,
                    isSelected = isSelected
                )
            }
        }
        return@setState copy(data = newData, selectedItems = newData.filter { it.isSelected }.size)
    }

    // TODO: find a better way
    fun uncheckAllItems() = setState {
        val newData = data.map { item ->
            return@map if (!item.isSelected) item else {
                HistoryItem(
                    id = item.id,
                    expression = item.expression,
                    result = item.result,
                    isSelected = false
                )
            }
        }
        return@setState copy(data = newData, selectedItems = 0)
    }

    // TODO: remove fakeSubscribe
    fun fakeSubscribe(subscriber: (HistoryState) -> Unit) {
        subscribeState(subscriber = subscriber)
    }

    override fun onCleared() {
        super.onCleared()
        getHistory.dispose()
    }
}