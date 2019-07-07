package com.manueldidonna.arch.view

import com.manueldidonna.arch.state.ArxState
import com.olegsheliakin.statebinder.StateBinder
import timber.log.Timber

class ArxStateBinderView<S : ArxState> : ArxBinderView<S> {
    // TODO: create ArxStateBinder
    private val stateBinder: StateBinder<S> = StateBinder.create()

    override fun dispatchNewState(state: S) {
        Timber.d("dispatchNewState for ${javaClass.simpleName}@${Integer.toHexString(hashCode())}")
        stateBinder.newState(state)
    }

    override fun <A : Any> bind(prop: (S) -> A, action: (A) -> Unit) {
        stateBinder.bind(prop, action)
    }
}