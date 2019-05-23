package com.manueldidonna.architex.mathevaluator.utils

inline fun checkDebug(debug: Boolean, condition: Boolean, lazyMessage: () -> Any) {
    if (debug)
        require(condition, lazyMessage)
}