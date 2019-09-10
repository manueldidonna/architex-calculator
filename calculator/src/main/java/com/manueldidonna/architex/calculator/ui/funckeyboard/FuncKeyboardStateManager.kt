package com.manueldidonna.architex.calculator.ui.funckeyboard

import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.architex.calculator.domain.DeleteConstantItemInteractor
import com.manueldidonna.architex.calculator.domain.GetConstantItemsInteractor
import com.manueldidonna.architexcalc.core.base.BaseStateManager
import com.manueldidonna.architexcalc.core.launchInteractor
import com.manueldidonna.architexcalc.database.Constant

internal data class FuncKeyboardState(
    val savedConstants: List<Constant> = listOf()
) : ArxState

internal class FuncKeyboardStateManager(
    initialState: FuncKeyboardState,
    getConstants: GetConstantItemsInteractor,
    private val deleteAllConstants: DeleteConstantItemInteractor
) : BaseStateManager<FuncKeyboardState>(initialState) {
    init {
        getConstants().observe().autoSubscribe { items ->
            setState { copy(savedConstants = items) }
        }
    }

    fun deleteAllConstants() {
        stateManagerScope.launchInteractor(deleteAllConstants, null)
    }
}