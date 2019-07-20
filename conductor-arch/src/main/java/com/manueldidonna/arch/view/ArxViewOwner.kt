package com.manueldidonna.arch.view

/**
 * An ArxViewOwner should connect [ArxView]s to his lifecycle
 */
interface ArxViewOwner {
    fun <V : ArxView> addView(view: V): V
    fun clearViews()
}

class BaseArxViewOwner : ArxViewOwner {
    private val views: MutableList<ArxView> = mutableListOf()

    override fun <V : ArxView> addView(view: V): V = view.also { views.add(it) }

    override fun clearViews() {
        views.clear()
    }
}

inline fun <reified V : ArxView> ArxViewOwner.addView(view: V, block: V.() -> Unit) {
    addView(view)
    view.block()
}