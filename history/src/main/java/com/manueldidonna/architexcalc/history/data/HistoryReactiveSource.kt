package com.manueldidonna.architexcalc.history.data

import com.manueldidonna.architexcalc.core.SubjectSource
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import io.reactivex.Flowable

class HistoryReactiveSource(
    private val historyDao: HistoryDao
) : SubjectSource<Unit, List<HistoryEntity>>() {
    override fun createObservable(params: Unit): Flowable<List<HistoryEntity>> {
        return historyDao.getHistoryEntities()
    }
}