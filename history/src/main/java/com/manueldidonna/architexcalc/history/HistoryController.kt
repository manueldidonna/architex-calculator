package com.manueldidonna.architexcalc.history

import android.view.View
import com.manueldidonna.arch.state.withState
import com.manueldidonna.arch.viewmodel.arxViewModel
import com.manueldidonna.architexcalc.core.base.BaseController
import com.manueldidonna.architexcalc.history.clearhistory.ClearHistoryDialog
import org.koin.core.parameter.parametersOf

class HistoryController : BaseController<HistoryView>(), ClearHistoryDialog.Listener {

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
                is HistoryEvent.ClearHistory -> withState(viewModel) { state ->
                    if (!state.askConfirmation)
                        onClearHistoryCallBack()
                    else ClearHistoryDialog.show(router, this)
                }
                is HistoryEvent.ClearSelectedItems -> viewModel.deleteSelectedItems()
                is HistoryEvent.SelectItem -> viewModel.selectItemById(event.itemId, event.isSelected)
            }
        }
    }

    override fun onAskAgainCallBack(askAgain: Boolean) {
        viewModel.setAskConfirmation(askAgain)
    }

    override fun onClearHistoryCallBack() {
        viewModel.deleteHistory()
    }

    override fun handleBack(): Boolean = withState(viewModel) { state ->
        if (state.isSelectionActive)
            viewModel.uncheckAllItems()
        return@withState state.isSelectionActive
    }
}