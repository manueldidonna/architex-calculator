package com.manueldidonna.arch.viewmodel

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ArxViewModel {
    /**
     * [ArxViewModelStore] should invoke onCleared() but that method cannot be exposed.
     * @see clear
     *
     * Called on ViewModel deletion.
     */
    @CallSuper
    protected open fun onCleared() {}

    @MainThread
    internal fun clear(){
        onCleared()
    }
}