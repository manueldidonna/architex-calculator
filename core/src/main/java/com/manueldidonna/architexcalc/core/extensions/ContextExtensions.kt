package com.manueldidonna.architexcalc.core.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.*

fun Context.clipboardManager() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

fun Context.saveTextToClipBoard(label: String, text: String) {
    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
        .primaryClip = ClipData.newPlainText(label, text)
}

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

inline fun Context.doWithStyledAttributes(
    set: AttributeSet?,
    @StyleableRes attrs: IntArray,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    block: TypedArray.() -> Unit
) {
    theme.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes).apply {
        try {
            block()
        } finally {
            recycle()
        }
    }
}

fun Int.pxToDp(): Float = (this * Resources.getSystem().displayMetrics.density)