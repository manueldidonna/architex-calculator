package com.manueldidonna.arch.viewmodel

import android.os.Bundle
import com.manueldidonna.arch.state.ArxState

data class ViewModelSavedState(val key: String, val state: ArxState)

class ArxViewModelStore {

    private val viewModels: HashMap<String, ArxViewModel> = hashMapOf()

    // TODO: check if there could be more than one instance per viewmodel class
    fun put(key: String, viewModel: ArxViewModel) {
        viewModels.put(key, viewModel)?.clear()
    }

    fun onSaveInstanceState(savedKey: String, outState: Bundle){
        // TODO: save viewmodels' state
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle){
        // TODO: restore viewmodels' state
    }

    fun get(key: String): ArxViewModel? = viewModels[key]

    // fun keys(): HashSet<String> = HashSet(viewModels.keys)

    fun clear() {
        viewModels.values.forEach(ArxViewModel::clear)
        viewModels.clear()
    }

}
