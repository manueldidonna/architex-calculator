package com.manueldidonna.arch.view

import android.view.View
import com.manueldidonna.arch.state.ArxState
import com.olegsheliakin.statebinder.StateBinder
import timber.log.Timber

abstract class ArxViewBinder<S : ArxState>(val view: View) : ArxRenderView<S> {

    // TODO: create ArxStateBinder
    private val stateBinder: StateBinder<S> = StateBinder.create()

    final override fun render(state: S) {
        Timber.d("Render state for ${javaClass.simpleName}@${Integer.toHexString(hashCode())}")
        stateBinder.newState(state)
    }

    protected fun <A : Any> bind(prop: (S) -> A, action: (A) -> Unit) {
        stateBinder.bind(prop, action)
    }
}