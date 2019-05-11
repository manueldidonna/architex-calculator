package com.manueldidonna.architexcalc.core.extensions

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.manueldidonna.architexcalc.core.ui.HorizontalSlideChangeHandler

private const val standardTransactionDuration = 240L

fun Router.setRootController(controller: Controller, tag: String? = null) {
    setRoot(RouterTransaction.with(controller).tag(tag))
}

fun Router.pushWithStandardTransaction(controller: Controller, tag: String? = null) {
    pushController(
        RouterTransaction.with(controller)
            .popChangeHandler(HorizontalSlideChangeHandler(standardTransactionDuration))
            .pushChangeHandler(HorizontalSlideChangeHandler(standardTransactionDuration))
            .tag(tag)
    )
}
