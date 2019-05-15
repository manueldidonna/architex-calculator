package com.manueldidonna.architexcalc.history

import com.manueldidonna.architexcalc.core.base.BaseViewModel
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.core.prefs.CorePreferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.history.data.DeleteHistoryInteractor
import com.manueldidonna.architexcalc.history.data.HistoryItem
import com.manueldidonna.architexcalc.history.data.HistoryReactiveSource

class HistoryViewModel(
    initialState: HistoryState,
    historyReactiveSource: HistoryReactiveSource,
    private val deleteHistory: DeleteHistoryInteractor,
    private val prefsManager: PreferencesManager
) : BaseViewModel<HistoryState>(initialState) {

    init {
        historyReactiveSource.create(Unit).observe().autoSubscribe { entities ->
            // todo: MOVE MAPPER AWAY FROM VIEWMODEL
            setState {
                copy(data = entities.map { entity -> HistoryItem(entity.id, entity.expression, entity.result) })
            }
        }

        prefsManager.observe(CorePreferences.ASK_CONFIRM).autoSubscribe { newValue ->
            setState { copy(askConfirmation = newValue) }
        }
    }

    fun setAskConfirmation(value: Boolean) {
        prefsManager.set(CorePreferences.ASK_CONFIRM, value)
    }

    fun deleteHistory() = withState {
        if (it.data.isNotEmpty())
            coroutineScope.launchInteractor(deleteHistory, DeleteHistoryInteractor.Params(deleteAll = true))
    }

    // TODO: remove fakeSubscribe
    fun fakeSubscribe(subscriber: (HistoryState) -> Unit) {
        subscribeState(subscriber = subscriber)
    }
}