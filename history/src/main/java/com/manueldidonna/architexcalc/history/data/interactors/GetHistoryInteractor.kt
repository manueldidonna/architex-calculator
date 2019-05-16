package com.manueldidonna.architexcalc.history.data.interactors

import com.manueldidonna.architexcalc.core.RxInteractor
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import com.manueldidonna.architexcalc.history.data.HistoryItem
import io.reactivex.Flowable

class GetHistoryInteractor(
    private val historyDao: HistoryDao
) : RxInteractor<Unit, List<HistoryItem>>() {
    override fun createObservable(params: Unit): Flowable<List<HistoryItem>> {
        return historyDao.getHistoryEntities().map { list ->
            list.map { entity ->
                HistoryItem(
                    id = entity.id,
                    expression = entity.expression,
                    result = entity.result,
                    isSelected = false
                )
            }
        }
    }
}