package com.manueldidonna.architexcalc.core.base

import android.view.View
import androidx.annotation.IdRes
import butterknife.ButterKnife
import butterknife.Unbinder
import com.manueldidonna.arch.view.ArxView

@Suppress("LeakingThis")
abstract class BaseContainerView(val view: View) : ArxView {
    protected val unbinder: Unbinder = ButterKnife.bind(this, view)

    inline fun <reified T : View> findView(@IdRes viewId: Int) = lazy {
        view.findViewById<T>(viewId)
    }
}