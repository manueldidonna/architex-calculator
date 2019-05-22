package com.manueldidonna.architex.mathevaluator

inline fun checkDebug(debug: Boolean, condition: Boolean, lazyMessage: () -> Any) {
    if (debug)
        require(condition, lazyMessage)
}