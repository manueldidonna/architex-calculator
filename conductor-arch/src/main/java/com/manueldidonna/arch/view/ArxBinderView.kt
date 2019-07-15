package com.manueldidonna.arch.view

import com.manueldidonna.arch.state.ArxState
import com.olegsheliakin.statebinder.StateBinder

/**
 * @see ArxStateBinderView for a sample implementation
 */
interface ArxBinderView<S> : ArxView {
    fun dispatchNewState(state: S)
    fun <A : Any> bind(prop: (S) -> A, action: (A) -> Unit)
}

class ArxStateBinderView<S : ArxState> : ArxBinderView<S> {
    // TODO: create ArxStateBinder
    private val stateBinder: StateBinder<S> = StateBinder.create()

    override fun dispatchNewState(state: S) {
        stateBinder.newState(state)
    }

    override fun <A : Any> bind(prop: (S) -> A, action: (A) -> Unit) {
        stateBinder.bind(prop, action)
    }
}