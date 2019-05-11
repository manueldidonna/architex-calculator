package com.manueldidonna.arch.controller

import android.view.View
import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ArxRxController : ArxController() {

    private var untilDetachSubscriptions = CompositeDisposable()
    private var untilDestroyViewSubscriptions = CompositeDisposable()

    @CallSuper
    override fun onAttach(view: View) {
        super.onAttach(view)
        if (untilDetachSubscriptions.isDisposed) {
            untilDetachSubscriptions = CompositeDisposable()
        }
    }

    @CallSuper
    override fun onDetach(view: View) {
        super.onDetach(view)
        untilDetachSubscriptions.dispose()
    }

    @CallSuper
    override fun onViewCreated(view: View) {
        if (untilDestroyViewSubscriptions.isDisposed) {
            untilDestroyViewSubscriptions = CompositeDisposable()
        }
    }

    @CallSuper
    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        untilDestroyViewSubscriptions.dispose()
    }

    protected fun Disposable.disposeOnDetach(): Disposable {
        untilDetachSubscriptions.add(this)
        return this
    }

    protected fun Disposable.disposeOnDestroyView(): Disposable {
        untilDestroyViewSubscriptions.add(this)
        return this
    }

}