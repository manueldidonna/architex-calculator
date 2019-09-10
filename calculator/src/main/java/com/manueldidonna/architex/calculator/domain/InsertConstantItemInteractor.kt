package com.manueldidonna.architex.calculator.domain

import com.manueldidonna.architexcalc.core.Interactor
import com.manueldidonna.architexcalc.database.ConstantRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertConstantItemInteractor(
    private val repository: ConstantRepository
) : Interactor<InsertConstantItemInteractor.Params> {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun invoke(param: Params) {
        repository.insert(param.name, param.value)
    }

    data class Params(val name: String, val value: String)
}