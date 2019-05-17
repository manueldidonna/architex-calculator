package com.manueldidonna.architexcalc.core.extensions

import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.manueldidonna.architexcalc.core.R

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
                dy < 0 && visibility == View.GONE -> show()
                dy < 0 && visibility == View.VISIBLE -> extend()
                dy > 0 && recyclerView.canScrollVertically(1) -> shrink()
                dy > 0 && !recyclerView.canScrollVertically(1) -> hide()
            }
            super.onScrolled(recyclerView, dx, dy)
        }
    })
}

inline fun EpoxyRecyclerView.buildModels(crossinline buildModels: EpoxyController.() -> Unit) {
    buildModelsWith {
        it.buildModels()
    }
}

inline fun <T> EpoxyRecyclerView.iterateAndBuildModels(
    data: List<T>,
    crossinline buildModels: EpoxyController.(index: Int, element: T) -> Unit
) = buildModelsWith { controller ->
    data.forEachIndexed { index, element ->
        controller.buildModels(index, element)
    }
}
