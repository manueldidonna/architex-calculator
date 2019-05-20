package com.manueldidonna.architexcalc.core.extensions

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes

fun Context.clipboardManager() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

fun Context.shortToast(@StringRes resText: Int) {
    Toast.makeText(this, resText, Toast.LENGTH_SHORT).show()
}

fun Context.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

@ColorInt
fun Context.resolveColor(@AttrRes attrId: Int): Int = TypedValue().also { typed ->
    theme.resolveAttribute(attrId, typed, true)
}.data

fun Int.pxToDp(): Float = (this * Resources.getSystem().displayMetrics.density)