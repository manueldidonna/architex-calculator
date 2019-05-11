package com.manueldidonna.arch.view

import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.manueldidonna.arch.state.ArxState

@Suppress("LeakingThis")
abstract class ArxButterBinder<S: ArxState>(view: View) : ArxViewBinder<S>(view){
    protected val unbinder: Unbinder = ButterKnife.bind(this, view)
}