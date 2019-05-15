package com.manueldidonna.architexcalc.core.extensions

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.clipboardManager() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

fun Context.shortToast(@StringRes resText: Int) {
    Toast.makeText(this, resText, Toast.LENGTH_SHORT).show()
}

fun Context.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Int.pxToDp(): Float = (this * Resources.getSystem().displayMetrics.density)