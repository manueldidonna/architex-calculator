package com.manueldidonna.architexcalc.history.domain

import com.manueldidonna.architexcalc.core.RxInteractor
import com.manueldidonna.architexcalc.database.History
import com.manueldidonna.architexcalc.database.HistoryRepository
import io.reactivex.Observable

class GetHistoryItemsInteractor(
    private val historyRepository: HistoryRepository
) : RxInteractor<Unit, List<History>>() {
    override fun createObservable(params: Unit): Observable<List<History>> {
        return historyRepository.observeAllEntities()
    }

    operator fun invoke(): GetHistoryItemsInteractor {
        invoke(Unit)
        return this
    }
}