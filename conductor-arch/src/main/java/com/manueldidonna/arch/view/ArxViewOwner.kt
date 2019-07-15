package com.manueldidonna.arch.view

/**
 * An ArxViewOwner should connect [ArxView]s to his lifecycle
 */
interface ArxViewOwner {
    fun addView(view: ArxView)
    fun clearViews()
}

inline fun <reified V : ArxView> ArxViewOwner.addView(view: V, block: V.() -> Unit) {
    addView(view)
    view.block()
}