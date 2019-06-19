package com.manueldidonna.architexcalc.core.base

import android.view.View
import androidx.annotation.IdRes
import butterknife.ButterKnife
import butterknife.Unbinder
import com.manueldidonna.arch.state.ArxState
import com.manueldidonna.arch.view.ArxInteractiveView
import com.manueldidonna.arch.view.ArxViewBinder
import com.manueldidonna.arch.view.createSubject
import io.reactivex.Observable

@Suppress("LeakingThis")
abstract class BaseContainerView<S : ArxState, E : Any>(view: View) : ArxViewBinder<S>(view),
    ArxInteractiveView<E> {

    inline fun <reified T : View> findView(@IdRes viewId: Int) = lazy {
        view.findViewById<T>(viewId)
    }

    protected val unbinder: Unbinder = ButterKnife.bind(this, view)

    private val subject = createSubject()

    final override fun getUserInteractionEvents(): Observable<E> {
        return subject
    }

    protected fun View.emitOnClick(event: E) {
        setOnClickListener { subject.onNext(event) }
    }

    protected fun emit(event: E) {
        subject.onNext(event)
    }
}