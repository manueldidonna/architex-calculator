package com.manueldidonna.architex.mathevaluator.utils

inline fun checkDebug(debug: Boolean, lazyCondition: () -> Boolean, lazyMessage: () -> Any) {
    if (debug)
        require(lazyCondition(), lazyMessage)
}