package com.manueldidonna.architexcalc.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.manueldidonna.arch.controller.ArxViewController
import com.manueldidonna.arch.controller.requireActivity
import com.manueldidonna.arch.view.ArxView
import com.manueldidonna.architexcalc.core.NavigationController
import org.koin.core.KoinComponent
import org.koin.core.scope.Scope

abstract class BaseController<V : ArxView> : ArxViewController<V>(),
    NavigationController, KoinComponent {

    protected val controllerScope: Scope by lazy { getKoin().createScope(ownerId) }

    @get:LayoutRes
    abstract val layoutRes: Int

    final override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(layoutRes, container, false)
    }

    @Suppress("UNCHECKED_CAST")
    final override fun <N> getNavigator(): N = requireActivity() as? N
        ?: throw IllegalStateException("Activity must implements the navigation interface")

    @CallSuper
    override fun onDestroy() {
        controllerScope.close()
    }
}