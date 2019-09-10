package com.manueldidonna.architexcalc.database

import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable

class ConstantRepository(private val queries: ConstantQueries) {
    fun insert(name: String, value: String) = queries.insert(name, value)
    fun deleteByName(constName: String) = queries.deleteByName(constName)
    fun deleteAll() = queries.deleteAll()
    fun observeAllEntities(): Observable<List<Constant>> = queries.selectAll().asObservable().mapToList()
}