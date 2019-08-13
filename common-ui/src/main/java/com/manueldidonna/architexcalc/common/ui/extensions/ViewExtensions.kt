package com.manueldidonna.architexcalc.common.ui.extensions

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.manueldidonna.architexcalc.common.ui.R

inline fun <reified T : View> View.findBy(@IdRes viewId: Int) = lazy { findViewById<T>(viewId) }

inline fun <reified T : View> View.find(@IdRes viewId: Int): T = findViewById(viewId)
inline fun <reified T : View> View.find(@IdRes viewId: Int, block: T.() -> Unit): T {
    return findViewById<T>(viewId).apply { block() }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(): T = layoutParams as T

fun View.setSelectableBackground() {
    val backgroundValue = TypedValue()
    context.theme.resolveAttribute(R.attr.selectableItemBackground, backgroundValue, true)
    setBackgroundResource(backgroundValue.resourceId)
    isClickable = true
    isFocusable = true
}

fun ExtendedFloatingActionButton.setRecyclerView(recycler: RecyclerView) {
    recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            when {
                // scroll up when fab is hidden
                dy < 0 && visibility == View.GONE -> visibility = View.VISIBLE
                // scroll up when fab is visible
                dy < 0 && visibility == View.VISIBLE -> extend()
                // scroll down but not on bottom
                dy > 0 && recyclerView.canScrollVertically(1) -> shrink()
                // on bottom
                dy > 0 && !recyclerView.canScrollVertically(1) -> visibility = View.GONE
            }
            super.onScrolled(recyclerView, dx, dy)
        }
    })
}
