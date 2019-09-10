package com.manueldidonna.architex.calculator.ui

import android.os.Parcelable
import com.manueldidonna.arch.state.ArxState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalculatorState(
    val expression: String = "",
    val result: String = "",
    val errorOnResult: Boolean = false,
    val tapBackToDelete: Boolean = false,
    val isClipboardEmpty: Boolean = true
) : ArxState, Parcelable
