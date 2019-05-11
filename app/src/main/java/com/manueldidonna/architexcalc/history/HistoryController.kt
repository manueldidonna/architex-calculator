package com.manueldidonna.architexcalc.history

import android.view.View
import com.manueldidonna.arch.state.withState
import com.manueldidonna.arch.viewmodel.arxViewModel
import com.manueldidonna.architexcalc.R
import com.manueldidonna.architexcalc.core.base.BaseController
import org.koin.core.parameter.parametersOf

class HistoryController : BaseController<HistoryView>(), DeleteHistoryDialog.Listener {

    override val layoutRes: Int = R.layout.controller_history

    private val viewModel by arxViewModel(HistoryState()) { state ->
        controllerScope.get<HistoryViewModel> { parametersOf(state) }
    }

    override fun onCreateControllerView(layoutView: View) = HistoryView(layoutView)

    override fun onControllerViewCreated(layoutView: View, controllerView: HistoryView) {
        viewModel.fakeSubscribe { state -> controllerView.render(state) }
        controllerView.subscribeUserInteractionEvents { event ->
            when (event) {
                is HistoryEvent.GoBack -> router.popController(this)
                is HistoryEvent.DeleteHistory -> withState(viewModel) { state ->
                    if (!state.askConfirmation)
                        onDeleteHistoryCallBack()
                    else DeleteHistoryDialog.show(router, this)
                }

            }
        }
    }

    override fun onAskAgainCallBack(askAgain: Boolean) {
        viewModel.setAskConfirmation(askAgain)
    }

    override fun onDeleteHistoryCallBack() {
        viewModel.deleteHistory()
    }

}