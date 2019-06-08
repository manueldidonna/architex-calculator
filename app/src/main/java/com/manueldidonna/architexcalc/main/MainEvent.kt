package com.manueldidonna.architexcalc.main

sealed class MainEvent {
    object History : MainEvent()
    object Settings : MainEvent()
    data class CopyPasteExpression(val text: String): MainEvent()
    data class CopyResult(val text: String): MainEvent()
}