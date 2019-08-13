package com.manueldidonna.architexcalc.common.ui.conductor

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

private const val standardTransactionDuration = 200L

fun Router.pushWithStandardTransaction(controller: Controller, tag: String? = null) {
    pushController(
        RouterTransaction.with(controller)
            .popChangeHandler(
                HorizontalSlideChangeHandler(
                    standardTransactionDuration
                )
            )
            .pushChangeHandler(
                HorizontalSlideChangeHandler(
                    standardTransactionDuration
                )
            )
            .tag(tag)
    )
}
