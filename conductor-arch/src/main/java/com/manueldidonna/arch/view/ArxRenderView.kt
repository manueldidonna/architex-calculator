package com.manueldidonna.arch.view

interface ArxRenderView<S> : ArxView {
    fun render(state: S)
}