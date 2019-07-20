package com.manueldidonna.arch.controller

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ReactiveArxController : StatelessArxController, ReactiveController {
    protected constructor() : super()
    protected constructor(args: Bundle?) : super(args)

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

    override fun Disposable.disposeOnDetach(): Disposable = apply {
        untilDetachSubscriptions.add(this)
    }

    override fun Disposable.disposeOnDestroyView(): Disposable = apply {
        untilDestroyViewSubscriptions.add(this)
    }
}