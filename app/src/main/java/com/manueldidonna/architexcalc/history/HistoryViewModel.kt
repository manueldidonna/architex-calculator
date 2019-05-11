package com.manueldidonna.architexcalc.history

import com.manueldidonna.arch.viewmodel.ArxStateViewModel
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.core.prefs.CorePreferences
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.data.interactors.DeleteHistoryInteractor
import com.manueldidonna.architexcalc.data.reactivesources.HistoryReactiveSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class HistoryViewModel(
    initialState: HistoryState,
    historyReactiveSource: HistoryReactiveSource,
    private val deleteHistory: DeleteHistoryInteractor,
    private val prefsManager: PreferencesManager
) : ArxStateViewModel<HistoryState>(initialState) {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        historyReactiveSource.create(Unit).observe()
            .subscribe {
                setState { copy(data = it) }
            }.disposeOnClear()

        prefsManager.apply {
            observe(CorePreferences.ASK_CONFIRM).subscribe { newValue ->
                setState { copy(askConfirmation = newValue) }
            }.disposeOnClear()
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
        subscribe(subscriber = subscriber)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}