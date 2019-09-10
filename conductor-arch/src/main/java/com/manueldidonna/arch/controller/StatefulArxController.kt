package com.manueldidonna.arch.controller

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.CallSuper
import com.manueldidonna.arch.conductorLazy
import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.statemanager.ArxStateManager
import com.manueldidonna.arch.statemanager.ArxStateManagerOwner
import timber.log.Timber

inline fun <reified SM : ArxStateManager<S>, S : ArxState> StatefulArxController<SM, S>.lazyStateManager(
    newState: S,
    crossinline stateManagerCreator: (state: S) -> SM
) = conductorLazy { stateManagerCreator(savedState { newState }) }

/**
 * A base controller that owns an [ArxStateManager].
 * It can save a state [S] among process if [saveStateOnProcessDeath] is true
 * The saved state could be retrieved with [savedState]
 */
abstract class StatefulArxController<SM : ArxStateManager<S>, S : ArxState> : ReactiveArxController,
    ArxStateManagerOwner<SM, S> {

    protected constructor() : super()
    protected constructor(args: Bundle?) : super(args)

    // TODO: default = false
    open val saveStateOnProcessDeath: Boolean = true

    @PublishedApi
    internal var internalSavedState: S? = null
        private set

    inline fun savedState(newState: () -> S): S {
        return internalSavedState?.also {
            Timber.d("savedState for $controllerId")
        } ?: newState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val state = stateManager.state
        if (saveStateOnProcessDeath && state is Parcelable)
            outState.putParcelable(INTERNAL_SAVED_STATE_TAG, state)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (saveStateOnProcessDeath) {
            internalSavedState = savedInstanceState.getParcelable<Parcelable>(INTERNAL_SAVED_STATE_TAG) as? S
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        stateManager.onCleared()
    }

    companion object {
        private const val INTERNAL_SAVED_STATE_TAG = "arx_controller:internalSavedState"
    }
}