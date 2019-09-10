package com.manueldidonna.architex.calculator.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.database.ConstantRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.manueldidonna.architexcalc.database.Constant

/**
 * Invoke this interactor passing a [Constant.name] or null to delete all items
 */
class DeleteConstantItemInteractor(private val constantRepository: ConstantRepository) : Interactor<String?> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun invoke(param: String?) {
        if (param == null)
            constantRepository.deleteAll()
        else
            constantRepository.deleteByName(param)
    }
}