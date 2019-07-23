package com.manueldidonna.architexcalc.database

import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable

class HistoryRepository(private val historyQueries: HistoryQueries) {
    fun insert(expression: String, result: String) = historyQueries.insert(expression, result)
    fun deleteById(entityId: Long) = historyQueries.deleteById(entityId)
    fun deleteAll() = historyQueries.deleteAll()
    fun observeAllEntities(): Observable<List<History>> = historyQueries.selectAll().asObservable().mapToList()
}