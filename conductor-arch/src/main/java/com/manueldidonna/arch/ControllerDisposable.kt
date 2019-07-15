package com.manueldidonna.arch

import android.view.View
import com.bluelinelabs.conductor.Controller
import io.reactivex.disposables.CompositeDisposable

class ControllerDisposable {
    var untilDetachSubscriptions = CompositeDisposable()
        private set
    var untilDestroyViewSubscriptions = CompositeDisposable()
        private set

    fun bindToControllerLifecycle(controller: Controller) {
        controller.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun preAttach(controller: Controller, view: View) {
                if (untilDetachSubscriptions.isDisposed) {
                    untilDetachSubscriptions = CompositeDisposable()
                }
            }

            override fun preDetach(controller: Controller, view: View) {
                untilDetachSubscriptions.dispose()
            }

            override fun postCreateView(controller: Controller, view: View) {
                if (untilDestroyViewSubscriptions.isDisposed) {
                    untilDestroyViewSubscriptions = CompositeDisposable()
                }
            }

            override fun preDestroyView(controller: Controller, view: View) {
                untilDestroyViewSubscriptions.dispose()
            }
        })
    }
}
