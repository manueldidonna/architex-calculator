package com.manueldidonna.architex.calculator.domain

import com.manueldidonna.architexcalc.core.RxInteractor
import com.manueldidonna.architexcalc.database.Constant
import com.manueldidonna.architexcalc.database.ConstantRepository
import io.reactivex.Observable

/**
 * Get history items sorted by an ascendant order (from the least recent)
 */
class GetConstantItemsInteractor(
    private val repository: ConstantRepository
) : RxInteractor<Unit, List<Constant>>() {
    override fun createObservable(params: Unit): Observable<List<Constant>> {
        return repository.observeAllEntities()
    }

    operator fun invoke(): GetConstantItemsInteractor {
        invoke(Unit)
        return this
    }
}