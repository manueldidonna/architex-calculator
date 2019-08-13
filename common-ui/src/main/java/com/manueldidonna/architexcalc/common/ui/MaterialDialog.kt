package com.manueldidonna.architexcalc.common.ui

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.manueldidonna.architexcalc.core.R
import com.manueldidonna.architexcalc.core.extensions.resolveColor

inline fun materialDialog(context: Context, dialogConstructor: MaterialDialog.() -> Unit): MaterialDialog {
    return MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).apply {
        window?.navigationBarColor = context.resolveColor(R.attr.colorSurface)
        dialogConstructor()
    }
}

inline fun materialDialog(
    context: Context,
    @LayoutRes customViewRes: Int,
    dialogConstructor: MaterialDialog.(View) -> Unit
): MaterialDialog = materialDialog(context) {
    customView(customViewRes)
    dialogConstructor(getCustomView())
}